package com.example.airline.leasing.domain.seatingmap

data class SeatingMapId(val value: Long)

interface SeatingMapIdGenerator {
    fun generate(): SeatingMapId
}