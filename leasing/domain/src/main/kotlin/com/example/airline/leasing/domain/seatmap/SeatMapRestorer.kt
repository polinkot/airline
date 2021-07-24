package com.example.airline.leasing.domain.seatmap

import com.example.airline.common.types.base.Version

object SeatMapRestorer {
    fun restore(
            id: SeatMapId,
            name: SeatMapName,
            seats: Set<Seat>,
            version: Version
    ): SeatMap {
        return SeatMap(
                id = id,
                name = name,
                seats = seats,
                version = version)
    }
}