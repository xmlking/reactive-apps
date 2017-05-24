package com.example

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.client.WebClient
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Flux


@SpringBootApplication
class UiApplication

@Controller
class MainController(@Value("\${app.mongoApiUrl}") val mongoApiUrl: String,
					 @Value("\${app.streamApiUrl}") val streamApiUrl: String) {

	@GetMapping("/")
	fun home(model: Model): String {
		val userList = WebClient.create(mongoApiUrl)
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
		return WebClient.create(streamApiUrl)
				.get()
				.uri("/sse/quotes")
				.accept(MediaType.APPLICATION_STREAM_JSON)
				.retrieve()
				.bodyToFlux(Quote::class.java)
				.share()
				.log()
	}

	@GetMapping("/guestbook")
	fun guestBook(model: Model): String {
		val entryList = WebClient.create(mongoApiUrl)
				.get()
				.uri("api/guestbook")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(GuestBookEntryDto::class.java)
				.log()

		model.addAttribute("entries", ReactiveDataDriverContextVariable(entryList, 1))
		return "guestbook"
	}

    @GetMapping(path = arrayOf("/guestbook/feed"), produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    @ResponseBody
    fun guestBookStream(): Flux<GuestBookEntryDto> {
        return WebClient.create(mongoApiUrl)
                .get()
                .uri("/sse/guestbook")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(GuestBookEntryDto::class.java)
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