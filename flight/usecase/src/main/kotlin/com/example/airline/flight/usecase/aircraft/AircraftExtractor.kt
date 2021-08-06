package com.example.airline.flight.usecase.aircraft

import com.example.airline.flight.domain.aircraft.Aircraft
import com.example.airline.flight.domain.aircraft.AircraftId

interface AircraftExtractor {

    fun getById(id: AircraftId): Aircraft?

    fun getAll(): List<Aircraft>
}