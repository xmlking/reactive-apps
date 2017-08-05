package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import reactor.core.publisher.toFlux
import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration
import java.time.Duration.ofMillis
import java.time.Instant
import java.util.*
import kotlin.coroutines.experimental.buildIterator
import kotlin.coroutines.experimental.buildSequence

@SpringBootApplication
class StreamApplication

@Configuration
class QuoteRoutes(val quoteHandler: QuoteHandler) {

    @Bean
    fun quoteRouter() = router {
        GET("/sse/quotes").nest {
            accept(TEXT_EVENT_STREAM, quoteHandler::fetchQuotesSSE)
            accept(APPLICATION_STREAM_JSON, quoteHandler::fetchQuotes)
        }
        GET("/sse/fibonacci").nest {
            accept(TEXT_EVENT_STREAM, quoteHandler::fetchFibonacciSSE)
            accept(APPLICATION_STREAM_JSON, quoteHandler::fetchFibonacci)
        }
    }

}

@Component
class QuoteHandler(val quoteGenerator: QuoteGenerator) {

    fun fetchQuotesSSE(req: ServerRequest) = ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote::class.java)

    fun fetchQuotes(req: ServerRequest) = ok()
            .contentType(APPLICATION_STREAM_JSON)
            .body(quoteGenerator.fetchQuoteStream(ofMillis(200)), Quote::class.java)

    fun fetchFibonacciSSE(req: ServerRequest) = ok()
            .contentType(TEXT_EVENT_STREAM)
            .body(quoteGenerator.fetchFibonacciStream(ofMillis(1000)), String::class.java)

    fun fetchFibonacci(req: ServerRequest) = ok()
            .contentType(APPLICATION_STREAM_JSON)
            .body(quoteGenerator.fetchFibonacciStream(ofMillis(1000)), String::class.java)
}

@Component
class QuoteGenerator {

    val mathContext = MathContext(2)

    val random = Random()

    val prices = listOf(
            Quote("CTXS", BigDecimal(82.26, mathContext)),
            Quote("DELL", BigDecimal(63.74, mathContext)),
            Quote("GOOG", BigDecimal(847.24, mathContext)),
            Quote("MSFT", BigDecimal(65.11, mathContext)),
            Quote("ORCL", BigDecimal(45.71, mathContext)),
            Quote("RHT", BigDecimal(84.29, mathContext)),
            Quote("VMW", BigDecimal(92.21, mathContext))
    )


    fun fetchQuoteStream(period: Duration) = Flux.generate({ 0 },
            { index, sink: SynchronousSink<Quote> ->
                sink.next(updateQuote(prices[index]))
                (index + 1) % prices.size
            }).zipWith(Flux.interval(period))
            .map { it.t1.copy(instant = Instant.now()) }
            .share()
            .log()


    private fun updateQuote(quote: Quote) = quote.copy(
            price = quote.price.add(quote.price.multiply(
                    BigDecimal(0.05 * random.nextDouble()), mathContext))
    )


    val fibonacci =  buildIterator {

        var a = 0L
        var b = 1L

        while (true) {
            yield(b)

            val next = a + b
            a = b; b = next
        }
    }

    fun fetchFibonacciStream(interval: Duration) = fibonacci.toFlux()
            .delayElements(interval)
            .map{it.toString()}
            .share()
            .log()

}

// NOTE: declared it in shared module `commons`
//data class Quote(val ticker: String, val price: BigDecimal, val instant: Instant = Instant.now())

fun main(args: Array<String>) {
    SpringApplication.run(StreamApplication::class.java, *args)
}


// Programmatic bootstrap - Start without spring boot
//fun main(args: Array<String>) {
//    val quoteGenerator =  QuoteGenerator();
//    val quoteHandler =  QuoteHandler(quoteGenerator);
//    val routes = QuoteRoutes(quoteHandler).quoteRouter()
//
//    val handler = ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(routes))
//    HttpServer.create(8082).newHandler(handler).block()
//
//    Thread.currentThread().join()
//}
