package com.example.airline.leasing.domain.seatmap

data class SeatMapId(val value: Long)

interface SeatMapIdGenerator {
    fun generate(): SeatMapId
}