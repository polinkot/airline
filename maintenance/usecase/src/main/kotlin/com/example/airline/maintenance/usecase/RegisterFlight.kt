package com.example.airline.maintenance.usecase

import com.example.airline.maintenance.domain.FlightId

interface RegisterFlight {
    fun execute(request: RegisterFlightRequest): FlightId
}