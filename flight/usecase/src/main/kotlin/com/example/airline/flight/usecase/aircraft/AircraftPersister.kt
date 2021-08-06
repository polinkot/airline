package com.example.airline.flight.usecase.aircraft

import com.example.airline.flight.domain.aircraft.Aircraft

interface AircraftPersister {
    fun save(aircraft: Aircraft)
}