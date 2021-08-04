package com.example.airline.maintenance.usecase

import com.example.airline.common.types.common.Airport
import com.example.airline.maintenance.domain.FlightId
import com.example.airline.maintenance.domain.FlightState

interface GetFlights {
    fun execute(): List<FlightInfo>
}

data class FlightInfo(
        val id: FlightId,
        val departureAirport: Airport,
        var arrivalAirport: Airport? = null,
        var state: FlightState = FlightState.REGISTERED
)