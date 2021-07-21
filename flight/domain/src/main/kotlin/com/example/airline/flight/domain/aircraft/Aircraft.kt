package com.example.airline.flight.domain.aircraft

import com.example.airline.common.types.base.DomainEntity
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer

data class AircraftId(val value: Long)

class Aircraft internal constructor(
        id: AircraftId,
        val manufacturer: Manufacturer,
        val seatsCount: Count,
        version: Version
) : DomainEntity<AircraftId>(id, version) {

    companion object {
        fun create(id: AircraftId,
                   manufacturer: Manufacturer,
                   seatsCount: Count
        ): Aircraft {
            return Aircraft(
                    id = id,
                    manufacturer = manufacturer,
                    seatsCount = seatsCount,
                    version = Version.new()
            ).apply {
                addEvent(AircraftInfoReceivedDomainEvent(aircraftId = this.id))
            }
        }
    }
}