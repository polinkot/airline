package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.aircraft.seat
import com.example.airline.leasing.domain.aircraft.seatMapId
import com.example.airline.leasing.domain.aircraft.seatMapName
import com.example.airline.leasing.domain.aircraft.version
import com.example.airline.leasing.domain.seatmap.SeatMapRestorer
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SeatMapRestorerTest {

    @Test
    fun `restore seatMap - success`() {
        val id = seatMapId()
        val name = seatMapName()
        val seat = seat()
        val seats = setOf(seat)
        val version = version()

        val seatMap = SeatMapRestorer.restore(
                id = id,
                name = name,
                seats = seats,
                version = version
        )

        seatMap.id shouldBe id
        seatMap.name shouldBe name
        seatMap.seats.size shouldBe 1
        val firstSeat = seatMap.seats.first()
        firstSeat.row shouldBe seat.row
        firstSeat.letter shouldBe seat.letter
        seatMap.version shouldBe version

        seatMap.popEvents().shouldBeEmpty()
    }
}