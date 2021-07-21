package com.example.airline.flight.domain.ticket

import com.example.airline.flight.domain.flight.FlightId

interface MoreThanHourTillDeparture {
    fun check(flightId: FlightId): Boolean
}