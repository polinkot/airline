package com.example.airline.flight.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FlightController {

    @GetMapping("/flight")
    @Suppress("FunctionOnlyReturningConstant")
    fun hello(): String {
        return "Hello, flight!"
    }
}