package com.example.airline.flight.usecase.rules

import com.example.airline.flight.domain.flight.AirportAllowsFlightOnTime
import com.example.airline.flight.usecase.flight.AirportIntegrationService
import java.time.OffsetDateTime

class AirportAllowsFlightOnTimeImpl(private val airportService: AirportIntegrationService) : AirportAllowsFlightOnTime {
    override fun check(datetime: OffsetDateTime): Boolean {
        return airportService.checkTime(datetime)
    }
}