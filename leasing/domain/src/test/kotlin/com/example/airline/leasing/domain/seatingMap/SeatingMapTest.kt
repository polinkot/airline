package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.aircraft.seating
import com.example.airline.leasing.domain.aircraft.seatingMapId
import com.example.airline.leasing.domain.aircraft.seatingMapName
import com.example.airline.leasing.domain.seatingmap.EmptySeatingMap
import com.example.airline.leasing.domain.seatingmap.Seating
import com.example.airline.leasing.domain.seatingmap.SeatingMap
import com.example.airline.leasing.domain.seatingmap.SeatingMapIdGenerator
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SeatingMapTest {

    val id = seatingMapId()

    private val idGenerator = object : SeatingMapIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create - success`() {
        val name = seatingMapName()
        val seating = seating()
        val seatings = setOf(seating)

        val result = SeatingMap.create(
                idGenerator = idGenerator,
                name = name,
                seatings = seatings
        )

        result shouldBeRight {
            it.id shouldBe id
            it.name shouldBe name
            it.seatings shouldContainExactly setOf(seating)
            it.popEvents().shouldBeEmpty()
        }
    }

    @Test
    fun `create - empty seatings`() {
        val name = seatingMapName()
        val seatings = emptySet<Seating>()

        val result = SeatingMap.create(
                idGenerator = idGenerator,
                name = name,
                seatings = seatings
        )

        result shouldBeLeft EmptySeatingMap
    }
}