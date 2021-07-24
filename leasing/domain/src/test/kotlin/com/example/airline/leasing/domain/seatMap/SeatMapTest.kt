package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.aircraft.seat
import com.example.airline.leasing.domain.aircraft.seatMapId
import com.example.airline.leasing.domain.aircraft.seatMapName
import com.example.airline.leasing.domain.seatmap.EmptySeatMap
import com.example.airline.leasing.domain.seatmap.Seat
import com.example.airline.leasing.domain.seatmap.SeatMap
import com.example.airline.leasing.domain.seatmap.SeatMapIdGenerator
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SeatMapTest {

    val id = seatMapId()

    private val idGenerator = object : SeatMapIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create - success`() {
        val name = seatMapName()
        val seat = seat()
        val seats = setOf(seat)

        val result = SeatMap.create(
                idGenerator = idGenerator,
                name = name,
                seats = seats
        )

        result shouldBeRight {
            it.id shouldBe id
            it.name shouldBe name
            it.seats shouldContainExactly setOf(seat)
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `create - empty seats`() {
        val name = seatMapName()
        val seats = emptySet<Seat>()

        val result = SeatMap.create(
                idGenerator = idGenerator,
                name = name,
                seats = seats
        )

        result shouldBeLeft EmptySeatMap
    }
}