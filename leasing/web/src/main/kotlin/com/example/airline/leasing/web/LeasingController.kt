package com.example.airline.leasing.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LeasingController {

    @GetMapping("/leasing")
    @Suppress("FunctionOnlyReturningConstant")
    fun hello(): String {
        return "Hello, leasing!"
    }
}