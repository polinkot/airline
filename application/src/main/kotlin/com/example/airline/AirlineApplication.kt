package com.example.airline

import com.example.airline.leasing.web.LeasingController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AirlineApplication

fun main(args: Array<String>) {
    runApplication<AirlineApplication>(*args)
}

@Bean
fun leasingController() = LeasingController()