package com.example.airline.leasing.domain.seatingmap

import com.example.airline.common.types.base.Version

object SeatingMapRestorer {
    fun restore(
            id: SeatingMapId,
            name: SeatingMapName,
            seatings: Set<Seating>,
            version: Version
    ): SeatingMap {
        return SeatingMap(
                id = id,
                name = name,
                seatings = seatings,
                version = version)
    }
}