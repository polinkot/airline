package com.example.airline.flight.usecase.aircraft

import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.flight.domain.aircraft.AircraftId

interface GetAircrafts {
    fun execute(): List<AircraftInfo>
}

data class AircraftInfo(val id: AircraftId,
                        val manufacturer: Manufacturer,
                        val seatsCount: Count)