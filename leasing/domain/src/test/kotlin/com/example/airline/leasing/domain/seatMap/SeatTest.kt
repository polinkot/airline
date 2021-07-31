package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.aircraft.seatLetter
import com.example.airline.leasing.domain.aircraft.seatRow
import com.example.airline.leasing.domain.seatmap.Seat
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SeatTest {

    @Test
    fun `create seat - success`() {
        val row = seatRow()
        val letter = seatLetter()

        val result = Seat.create(
                row = row,
                letter = letter)

        result.row shouldBe row
        result.letter shouldBe letter
    }
}