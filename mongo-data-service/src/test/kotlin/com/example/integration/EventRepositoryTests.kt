package com.example.integration

import org.hamcrest.CoreMatchers.*
import com.example.repository.EventRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Order
import reactor.core.publisher.test
import java.time.Duration


class EventRepositoryTests : AbstractIntegrationTests() {

    @Autowired lateinit var eventRepository: EventRepository

    @Test
    fun `Count users correctly`() {
        val eventCount = eventRepository.count().block(Duration.ofSeconds(2))
            assertEquals(eventCount, 6)
    }

    @Test
    fun `Sort users correctly`() {
        val events = eventRepository.findAll(Sort.by(Order(ASC, "current")))
        events
                .test()
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun `Filter and page users correctly`() {
        val pagedEvents =  eventRepository.findByCurrentOrderByStart(true, PageRequest.of(0, 5) );
        pagedEvents
                .test()
                .consumeNextWith { actual ->
                    assertThat(actual.current, `is`(true))
                    println(actual)
                }
                .verifyComplete()
    }
}