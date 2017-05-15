package com.example


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@SpringBootApplication
class Application : WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry?) {
        registry!!.addMapping("/cors/config")
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

}