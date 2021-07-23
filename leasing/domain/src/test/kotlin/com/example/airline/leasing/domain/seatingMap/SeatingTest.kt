package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.aircraft.seatingRow
import com.example.airline.leasing.domain.aircraft.seatingSeat
import com.example.airline.leasing.domain.seatingmap.Seating
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SeatingTest {

    @Test
    fun `create seating - success`() {
        val row = seatingRow()
        val seat = seatingSeat()

        val result = Seating.create(
                row = row,
                seat = seat)

        result.row shouldBe row
        result.seat shouldBe seat
    }
}