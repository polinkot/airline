package com.example.airline.leasing.usecase.aircraft

import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.aircraft.AircraftContractNumber
import com.example.airline.leasing.domain.aircraft.AircraftId
import com.example.airline.leasing.domain.aircraft.AircraftRegistrationNumber
import java.time.OffsetDateTime

interface GetAircrafts {
    fun execute(): List<AircraftInfo>
}

data class AircraftInfo(
        val id: AircraftId,
        val manufacturer: Manufacturer,
        val releaseDate: OffsetDateTime,
        val registrationNumber: AircraftRegistrationNumber,
        val contractNumber: AircraftContractNumber
)