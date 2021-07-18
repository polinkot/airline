package com.example.airline

//import com.example.airline.flight.web.FlightController
import com.example.airline.leasing.web.LeasingController
//import com.example.airline.maintenance.web.MaintenanceController
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

//@Bean
//fun flightController() = FlightController()
//
//@Bean
//fun maintenanceController() = MaintenanceController()