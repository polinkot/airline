package com.example.airline.flight.domain.ticket

import com.example.airline.flight.domain.flight.FlightId

interface EnoughTimeToDeparture {
    fun check(flightId: FlightId, hours: Long): Boolean
}