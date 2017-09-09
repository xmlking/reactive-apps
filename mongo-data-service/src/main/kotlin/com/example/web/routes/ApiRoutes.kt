package com.example.web.routes

import com.example.web.handler.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class ApiRoutes(val eventHandler: EventHandler,
                val guestBookHandler: GuestBookHandler,
                val userHandler: UserHandler) {
    @Bean
    fun apiRouter() = router {
        (accept(APPLICATION_JSON) and "/api").nest {

            "/events".nest {
                GET("/", eventHandler::findAll)
                GET("/{id}", eventHandler::findOne)
            }

            // users
            "/users".nest {
                GET("/", userHandler::findAll)
                POST("/", userHandler::create)
                GET("/{login}", userHandler::findOne)
            }
            "/staff".nest {
                GET("/", userHandler::findAllStaff)
                GET("/{login}", userHandler::findOneStaff)
            }

            // guest book
            "/guestbook".nest {
                GET("/", guestBookHandler::findAll)
                POST("/", guestBookHandler::create)
                GET("/{id}", guestBookHandler::findOne)
            }

        }

        GET("/sse/guestbook").nest {
            accept(TEXT_EVENT_STREAM, guestBookHandler::fetchSSE)
            accept(APPLICATION_STREAM_JSON, guestBookHandler::fetch)
        }
    }
}
