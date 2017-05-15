package com.example

import com.example.repository.EventRepository
import com.example.repository.UserRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer(val userRepository: UserRepository,
                          val eventRepository: EventRepository) {

    @PostConstruct
    fun init() {
        userRepository.initData()
        eventRepository.initData()
    }
}