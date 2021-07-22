package com.example.airline.leasing.domain.aircraft

import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.seatingmap.SeatingMapId
import java.time.OffsetDateTime

@Suppress("LongParameterList")
class Aircraft internal constructor(
        id: AircraftId,
        val manufacturer: Manufacturer,
        val payload: AircraftPayload,
        val releaseDate: OffsetDateTime,
        val registrationNumber: AircraftRegistrationNumber,
        val contractNumber: AircraftContractNumber,
        val seatingMapId: SeatingMapId,
        version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun create(idGenerator: AircraftIdGenerator,
                   manufacturer: Manufacturer,
                   payload: AircraftPayload,
                   releaseDate: OffsetDateTime,
                   registrationNumber: AircraftRegistrationNumber,
                   contractNumber: AircraftContractNumber,
                   seatingMapId: SeatingMapId
        ): Aircraft {
            return Aircraft(
                    id = idGenerator.generate(),
                    manufacturer = manufacturer,
                    payload = payload,
                    releaseDate = releaseDate,
                    registrationNumber = registrationNumber,
                    contractNumber = contractNumber,
                    seatingMapId = seatingMapId,
                    version = Version.new()
            ).apply {
                addEvent(AircraftCreatedDomainEvent(aircraftId = this.id))
            }
        }
    }
}