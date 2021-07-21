package com.example.airline.flight.domain.flight

import com.example.airline.flight.domain.aircraft.AircraftId
import java.time.OffsetDateTime

interface AircraftIsAvailableOnTime {
    fun check(aircraftId: AircraftId, datetime: OffsetDateTime): Boolean
}