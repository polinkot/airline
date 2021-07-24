package com.example.airline.leasing.domain.aircraft

import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.seatmap.SeatMapId
import java.time.OffsetDateTime

@Suppress("LongParameterList")
object AircraftRestorer {
    fun restore(
            id: AircraftId,
            manufacturer: Manufacturer,
            payload: AircraftPayload,
            releaseDate: OffsetDateTime,
            registrationNumber: AircraftRegistrationNumber,
            contractNumber: AircraftContractNumber,
            seatMapId: SeatMapId,
            version: Version
    ): Aircraft {
        return Aircraft(
                id = id,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber,
                seatMapId = seatMapId,
                version = version)
    }
}