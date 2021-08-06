package com.example.airline.flight.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.DomainEntity
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.common.types.error.BusinessError

data class AircraftId(val value: Long)

class Aircraft internal constructor(
        id: AircraftId,
        val manufacturer: Manufacturer,
        val seatsCount: Count,
        version: Version
) : DomainEntity<AircraftId>(id, version) {

    companion object {
        fun receiveInfo(id: AircraftId,
                        manufacturer: Manufacturer,
                        seatsCount: Count
        ): Either<EmptySeatMapError, Aircraft> {
            if (seatsCount == Count(0)) {
                return EmptySeatMapError.left()
            }

            return Aircraft(
                    id = id,
                    manufacturer = manufacturer,
                    seatsCount = seatsCount,
                    version = Version.new()
            ).apply {
                addEvent(AircraftInfoReceivedDomainEvent(aircraftId = this.id))
            }.right()
        }
    }
}

object EmptySeatMapError : BusinessError