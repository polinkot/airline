package com.example.airline.maintenance.domain

interface FlightPersister {
    fun save(flight: Flight)
}