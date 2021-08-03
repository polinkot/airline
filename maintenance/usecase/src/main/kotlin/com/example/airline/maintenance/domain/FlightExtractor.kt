package com.example.airline.maintenance.domain

interface FlightExtractor {

    fun getById(id: FlightId): Flight?

    fun getAll(): List<Flight>
}