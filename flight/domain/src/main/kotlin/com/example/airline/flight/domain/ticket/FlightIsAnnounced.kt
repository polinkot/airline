package com.example.airline.flight.domain.ticket

import com.example.airline.flight.domain.flight.FlightId

interface FlightIsAnnounced {
    fun check(flightId: FlightId): Boolean
}