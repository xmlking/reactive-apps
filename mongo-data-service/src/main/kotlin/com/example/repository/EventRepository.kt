package com.example.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.example.domain.*
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface  EventRepository : ReactiveMongoRepository<Event, String> {
    @Tailable fun findBy(): Flux<Event>
    fun findByCurrentOrderByStart(current : Boolean, pageable: Pageable) : Flux<Event>
}

@Repository
class EventRepository1(val template: ReactiveMongoTemplate,
                      val objectMapper: ObjectMapper) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun initData() {
        if (count().block() == 0L) {
            val eventsResource = ClassPathResource("data/events.json")
            val events: List<Event> = objectMapper.readValue(eventsResource.inputStream)
            events.forEach { save(it).block() }
            logger.info("Events data initialization complete")
        }
    }

    fun count() = template.count<Event>()
    fun save(event: Event) = template.save(event)
}