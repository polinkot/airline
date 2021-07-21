package com.example.airline.flight.domain.aircraft

import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer

object AircraftRestorer {
    fun restore(
            id: AircraftId,
            manufacturer: Manufacturer,
            seatsCount: Count,
            version: Version
    ): Aircraft {
        return Aircraft(
                id = id,
                manufacturer = manufacturer,
                seatsCount = seatsCount,
                version = version)
    }
}