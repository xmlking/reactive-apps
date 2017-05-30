package com.example

import org.hibernate.validator.constraints.NotBlank
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.reactive.function.client.WebClient
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid


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
				.accept(APPLICATION_JSON)
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

	@GetMapping(path = arrayOf("/quotes/feed"), produces = arrayOf(TEXT_EVENT_STREAM_VALUE))
	@ResponseBody
	fun fetchQuotesStream(): Flux<Quote> {
		return WebClient.create(streamApiUrl)
				.get()
				.uri("/sse/quotes")
				.accept(APPLICATION_STREAM_JSON)
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
				.accept(APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(GuestBookEntryDTO::class.java)
				.log()

		model.addAttribute("entries", ReactiveDataDriverContextVariable(entryList, 1)) // buffers size = 1
		return "guestbook"
	}

    @PostMapping("/guestbook")
	@ResponseBody
    fun postGuestBook(@Valid guestBookEntryVO: GuestBookEntryVO, result: BindingResult): Mono<GuestBookEntryDTO>  {
		if(result.hasErrors()) {
			println(result.allErrors)
            throw Error("allErrors")
		}
		return WebClient.create(mongoApiUrl)
                .post()
                .uri("api/guestbook")
                .accept(APPLICATION_JSON)
				.syncBody(guestBookEntryVO)
                .exchange()
                .flatMap{response -> response.bodyToMono(GuestBookEntryDTO::class.java)}
                .log()
    }

    @GetMapping(path = arrayOf("/guestbook/feed"), produces = arrayOf(TEXT_EVENT_STREAM_VALUE))
    @ResponseBody
    fun guestBookStream(): Flux<GuestBookEntryDTO> {
        return WebClient.create(mongoApiUrl)
                .get()
                .uri("/sse/guestbook")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(GuestBookEntryDTO::class.java)
                .share()
                .log()
    }

    @GetMapping(path = arrayOf("/guestbook/feed_html"), produces = arrayOf(TEXT_EVENT_STREAM_VALUE))
    fun guestBookHtmlStream(model: Model): String {
		val guestBookStream = WebClient.create(mongoApiUrl)
                .get()
                .uri("/sse/guestbook")
                .accept(APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(GuestBookEntryDTO::class.java)
				.share()
                .log()

		model.addAttribute("entries", ReactiveDataDriverContextVariable(guestBookStream, 1)) // buffers size = 1
		// Will use the same "guestbook" template, but only a fragment: the `entries` block.
		return "guestbook :: #entries"
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
data class GuestBookEntryVO(
        val name: String,
        @NotBlank(message = "comment can't be empty!")
        val comment: String
)

fun main(args: Array<String>) {
    SpringApplication.run(UiApplication::class.java, *args)
}