package com.example.integration

import com.example.repository.EventRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Order
import reactor.test.test
import java.time.Duration


class EventRepositoryTests : AbstractIntegrationTests() {

    @Autowired lateinit var eventRepository: EventRepository

    @Test
    fun `Count users correctly`() {
        val eventCount = eventRepository.count().block(Duration.ofSeconds(2))
            assertEquals(eventCount, 6L)
    }

    @Test
    fun `Sort users correctly`() {
        val events = eventRepository.findAll(Sort.by(Order(ASC, "current")))
        events
                .test()
                .expectNextCount(6L)
                .verifyComplete()
    }

    @Test
    fun `Filter and page users correctly`() {
        val pagedEvents =  eventRepository.findByCurrentOrderByStart(true, PageRequest.of(0, 5) );
        pagedEvents
                .test()
                .consumeNextWith { actual ->
                    assertEquals(actual.current,true)
                    println(actual)
                }
                .verifyComplete()
    }
}
