package com.example.airline.maintenance.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MaintenanceController {

    @GetMapping("/maintenance")
    @Suppress("FunctionOnlyReturningConstant")
    fun hello(): String {
        return "Hello, maintenance!"
    }
}