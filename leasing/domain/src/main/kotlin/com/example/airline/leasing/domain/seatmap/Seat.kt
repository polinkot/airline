package com.example.airline.leasing.domain.seatmap

import com.example.airline.common.types.base.ValueObject

/**
 * Кресло в салоне. Ряд + место. Например 5B.
 */
data class Seat internal constructor(
        val row: Row,
        val letter: Letter
) : ValueObject {

    companion object {
        fun create(row: Row, letter: Letter): Seat {
            return Seat(row = row, letter = letter)
        }
    }
}