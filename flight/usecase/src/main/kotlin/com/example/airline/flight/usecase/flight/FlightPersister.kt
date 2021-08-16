package com.example.airline.flight.usecase.flight

import com.example.airline.flight.domain.flight.Flight

interface FlightPersister {
    fun save(flight: Flight)
}