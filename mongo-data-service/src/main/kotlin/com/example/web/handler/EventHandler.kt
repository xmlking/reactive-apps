package com.example.web.handler

import com.example.domain.Event
import com.example.repository.EventRepository
import com.example.util.json
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok


@Component
class EventHandler(val repository: EventRepository) {

    fun findOne(req: ServerRequest) = ok().json().body(repository.findById(req.pathVariable("id")))

    fun findAll(req: ServerRequest) = ok().json().body(repository.findAll())

}