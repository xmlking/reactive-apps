package com.example

import com.example.util.run
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    run(DemoApplication::class, *args)
}



