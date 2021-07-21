package com.example.airline.flight.domain.flight

import java.time.OffsetDateTime

interface AirportAllowsFlightOnTime {
    fun check(datetime: OffsetDateTime): Boolean
}