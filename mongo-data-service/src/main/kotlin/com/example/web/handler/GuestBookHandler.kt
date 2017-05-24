package com.example.web.handler

import com.example.Quote
import com.example.domain.GuestBookEntry
import com.example.domain.User
import com.example.repository.GuestBookRepository
import com.example.util.json
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.toMono
import java.net.URI
import java.time.Duration


@Component
class GuestBookHandler(val repository: GuestBookRepository) {

    fun findOne(req: ServerRequest) = ok().json().body(repository.findOne(req.pathVariable("id")))

    fun findAll(req: ServerRequest) = ok().json().body(repository.findAll())

    fun create(req: ServerRequest) = ok().json().body(repository.save(req.bodyToMono<GuestBookEntry>()))

    fun fetchSSE(req: ServerRequest) = ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(repository.tailAll(), GuestBookEntry::class.java)

    fun fetch(req: ServerRequest) = ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(repository.tailAll(), GuestBookEntry::class.java)
}