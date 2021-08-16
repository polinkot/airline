package com.example.airline.flight.usecase.flight

import com.example.airline.common.types.common.Airport
import com.example.airline.flight.domain.flight.FlightId
import java.time.OffsetDateTime

interface GetFlights {
    fun execute(): List<FlightInfo>
}

data class FlightInfo(
        val id: FlightId,
        val departureAirport: Airport,
        var arrivalAirport: Airport,
        val flightDate: OffsetDateTime
)