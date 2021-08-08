package com.example.airline.flight.usecase.flight

import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.flight.Flight
import com.example.airline.flight.domain.flight.FlightId

interface FlightExtractor {

    fun getById(id: FlightId): Flight?

    fun getAll(): List<Flight>

    fun getByAircraftId(aircraftId: AircraftId): List<Flight>
}