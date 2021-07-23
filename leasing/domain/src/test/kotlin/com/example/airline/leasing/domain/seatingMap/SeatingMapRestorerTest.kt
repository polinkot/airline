package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.aircraft.seating
import com.example.airline.leasing.domain.aircraft.seatingMapId
import com.example.airline.leasing.domain.aircraft.seatingMapName
import com.example.airline.leasing.domain.aircraft.version
import com.example.airline.leasing.domain.seatingmap.SeatingMapRestorer
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SeatingMapRestorerTest {

    @Test
    fun `restore seatingMap - success`() {
        val id = seatingMapId()
        val name = seatingMapName()
        val seating = seating()
        val seatings = setOf(seating)
        val version = version()

        val seatingMap = SeatingMapRestorer.restore(
                id = id,
                name = name,
                seatings = seatings,
                version = version
        )

        seatingMap.id shouldBe id
        seatingMap.name shouldBe name
        seatingMap.seatings.size shouldBe 1
        val firstSeating = seatingMap.seatings.first()
        firstSeating.row shouldBe seating.row
        firstSeating.seat shouldBe seating.seat
        seatingMap.version shouldBe version

        seatingMap.popEvents().shouldBeEmpty()
    }
}