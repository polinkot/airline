package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.common.types.error.BusinessError
import java.time.OffsetDateTime

@Suppress("LongParameterList")
class Aircraft internal constructor(
        id: AircraftId,
        val manufacturer: Manufacturer,
        val payload: AircraftPayload,
        val releaseDate: OffsetDateTime,
        val registrationNumber: AircraftRegistrationNumber,
        val contractNumber: AircraftContractNumber,
        val seats: Set<Seat>,
        version: Version
) : AggregateRoot<AircraftId>(id, version) {

    companion object {
        fun create(idGenerator: AircraftIdGenerator,
                   manufacturer: Manufacturer,
                   payload: AircraftPayload,
                   releaseDate: OffsetDateTime,
                   registrationNumber: AircraftRegistrationNumber,
                   contractNumber: AircraftContractNumber,
                   seats: Set<Seat>
        ): Either<EmptySeatMapError, Aircraft> {
            if (seats.isNullOrEmpty()) {
                return EmptySeatMapError.left()
            }

            return Aircraft(
                    id = idGenerator.generate(),
                    manufacturer = manufacturer,
                    payload = payload,
                    releaseDate = releaseDate,
                    registrationNumber = registrationNumber,
                    contractNumber = contractNumber,
                    seats = seats,
                    version = Version.new()
            ).apply {
                addEvent(AircraftCreatedDomainEvent(aircraftId = this.id))
            }.right()
        }
    }
}

object EmptySeatMapError : BusinessError