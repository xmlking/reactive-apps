package com.example.web.handler

import com.example.domain.GuestBookEntry
import com.example.repository.GuestBookRepository
import com.example.util.json
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import java.util.*

@Component
class GuestBookHandler(val repository: GuestBookRepository) {

    fun findOne(req: ServerRequest) = ok().json().body(repository.findOne(req.pathVariable("id")))

    fun findAll(req: ServerRequest) = ok().json().body(repository.findAll())

    fun create(req: ServerRequest) = ok().json().body(repository.save(req.bodyToMono<GuestBookEntry>()))

    fun fetchSSE(req: ServerRequest) = ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(repository.tailByTimestampGreaterThan((GregorianCalendar.getInstance().time)), GuestBookEntry::class.java)

    fun fetch(req: ServerRequest) = ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(repository.tailByTimestampGreaterThan((GregorianCalendar.getInstance().time)), GuestBookEntry::class.java)
}