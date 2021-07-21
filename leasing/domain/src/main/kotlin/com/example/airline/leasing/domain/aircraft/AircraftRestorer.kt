package com.example.airline.leasing.domain.aircraft

import com.example.airline.common.types.base.Version
import com.example.airline.leasing.domain.seatingmap.SeatingMap
import java.time.OffsetDateTime

@Suppress("LongParameterList")
object AircraftRestorer {

    fun restore(
            id: AircraftId,
            manufacturer: AircraftManufacturer,
            payload: AircraftPayload,
            releaseDate: OffsetDateTime,
            registrationNumber: AircraftRegistrationNumber,
            contractNumber: AircraftContractNumber,
            seatingMap: SeatingMap,
            version: Version
    ): Aircraft {

        return Aircraft(
                id = id,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber,
                seatingMap = seatingMap,
                version = version)
    }
}