package com.example.airline.leasing.domain.seatingmap

import com.example.airline.common.types.base.ValueObject

data class Seating internal constructor(
        val row: SeatingRow,
        val seat: SeatingSeat
) : ValueObject {

    companion object {
        fun create(row: SeatingRow,
                   seat: SeatingSeat
        ): Seating {
            return Seating(row = row, seat = seat)
        }
    }
}