package com.example.airline

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AirlineApplication

fun main(args: Array<String>) {
    runApplication<AirlineApplication>(*args)
}
