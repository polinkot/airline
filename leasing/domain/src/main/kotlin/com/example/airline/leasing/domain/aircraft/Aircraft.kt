package com.example.airline.leasing.domain.aircraft

import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.leasing.domain.seatingmap.SeatingMap
import java.time.OffsetDateTime

@Suppress("LongParameterList")
class Aircraft internal constructor(
        id: AircraftId,
        val manufacturer: AircraftManufacturer,
        val payload: AircraftPayload,
        val releaseDate: OffsetDateTime,
        val registrationNumber: AircraftRegistrationNumber,
        val contractNumber: AircraftContractNumber,
        val seatingMap: SeatingMap,
        version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun create(idGenerator: AircraftIdGenerator,
                   manufacturer: AircraftManufacturer,
                   payload: AircraftPayload,
                   releaseDate: OffsetDateTime,
                   registrationNumber: AircraftRegistrationNumber,
                   contractNumber: AircraftContractNumber,
                   seatingMap: SeatingMap
        ): Aircraft {
            return Aircraft(
                    id = idGenerator.generate(),
                    manufacturer = manufacturer,
                    payload = payload,
                    releaseDate = releaseDate,
                    registrationNumber = registrationNumber,
                    contractNumber = contractNumber,
                    seatingMap = seatingMap,
                    version = Version.new()
            ).apply {
                addEvent(AircraftCreatedDomainEvent(aircraftId = this.id))
            }
        }
    }
}