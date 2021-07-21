package com.example.airline.flight.domain.flight

data class FlightId(val value: Long)

interface FlightIdGenerator {
    fun generate(): FlightId
}