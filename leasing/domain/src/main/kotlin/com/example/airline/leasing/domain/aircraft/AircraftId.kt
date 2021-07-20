package com.example.airline.leasing.domain.aircraft

data class AircraftId(val value: Long)

interface AircraftIdGenerator {
    fun generate(): AircraftId
}