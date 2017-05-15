package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration
import java.time.Instant
import java.util.*
import reactor.core.publisher.Mono



@SpringBootApplication
class UiApplication

@Controller
class MainController {


	@GetMapping("/")
	fun home(model: Model): String {
		val userList = WebClient.create("http://localhost:8081")
				.get()
				.uri("api/staff")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(User::class.java)
				.log()

//		model.addAttribute("users", userList.collectList().block());
		model.addAttribute("users", ReactiveDataDriverContextVariable(userList, 1))
		return "index";
	}

	@GetMapping("/quotes")
	fun quotes(): String {
		return "quotes"
	}

	@GetMapping(path = arrayOf("/quotes/feed"), produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
	@ResponseBody
	fun fetchQuotesStream(): Flux<Quote> {
		return WebClient.create("http://localhost:8082")
				.get()
				.uri("/sse/quotes")
				.accept(MediaType.APPLICATION_STREAM_JSON)
				.retrieve()
				.bodyToFlux(Quote::class.java)
				.share()
				.log()
	}

}


data class User(
		val login: String,
		val firstname: String,
		val lastname: String,
		val email: String?,
		val company: String? = null,
		val emailHash: String? = null,
		val photoUrl: String? = null
)


fun main(args: Array<String>) {
    SpringApplication.run(UiApplication::class.java, *args)
}