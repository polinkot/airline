package com.example.airline.maintenance.usecase

import com.example.airline.maintenance.domain.Flight
import com.example.airline.maintenance.domain.FlightId

interface FlightExtractor {

    fun getById(id: FlightId): Flight?

    fun getAll(): List<Flight>
}