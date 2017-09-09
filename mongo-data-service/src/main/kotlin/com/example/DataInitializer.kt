package com.example

import com.example.repository.EventRepository1
import com.example.repository.GuestBookRepository
import com.example.repository.UserRepository
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DataInitializer(val userRepository: UserRepository,
                          val guestBookRepository: GuestBookRepository,
                          val eventRepository: EventRepository1) {

    @EventListener(ContextRefreshedEvent::class)
    fun init() {
        userRepository.initData()
        guestBookRepository.initData()
        eventRepository.initData()
    }
}