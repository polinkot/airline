package com.example.airline.maintenance.usecase

import com.example.airline.maintenance.domain.Flight

interface FlightPersister {
    fun save(flight: Flight)
}