package com.example.web.handler

import com.example.domain.GuestBookEntry
import com.example.repository.GuestBookRepository
import com.example.util.json
import com.example.util.jsonStream
import com.example.util.textStream
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

    fun fetchSSE(req: ServerRequest) = ok().textStream()
            .body(repository.tailByTimestampGreaterThan((GregorianCalendar.getInstance().time)))

    fun fetch(req: ServerRequest) = ok().jsonStream()
            .body(repository.tailByTimestampGreaterThan((GregorianCalendar.getInstance().time)))
}