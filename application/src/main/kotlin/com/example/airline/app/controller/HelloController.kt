package com.example.airline.app.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/")
    @Suppress("FunctionOnlyReturningConstant")
    fun hello(): String {
        return "Hello, world!"
    }
}