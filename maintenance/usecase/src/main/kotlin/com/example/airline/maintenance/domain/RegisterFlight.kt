package com.example.airline.maintenance.domain

interface RegisterFlight {
    fun execute(request: RegisterFlightRequest): FlightId
}