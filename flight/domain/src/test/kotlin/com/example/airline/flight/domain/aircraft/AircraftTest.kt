package com.example.airline.flight.domain.aircraft

import com.example.airline.common.types.common.Count
import com.example.airline.flight.domain.aircraftId
import com.example.airline.flight.domain.count
import com.example.airline.flight.domain.manufacturer
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftTest {

    @Test
    fun `receiveInfo aircraft - success`() {
        val id = aircraftId()
        val manufacturer = manufacturer()
        val seatsCount = count()

        val result = Aircraft.receiveInfo(id = id,
                manufacturer = manufacturer,
                seatsCount = seatsCount)

        result shouldBeRight {
            it.id shouldBe id
            it.manufacturer shouldBe manufacturer
            it.seatsCount shouldBe seatsCount

            it.popEvents() shouldContainExactly listOf(AircraftInfoReceivedDomainEvent(id))
        }
    }

    @Test
    fun `receiveInfo aircraft - no seats`() {
        val result = Aircraft.receiveInfo(id = aircraftId(),
                manufacturer = manufacturer(),
                seatsCount = Count(0))

        result shouldBeLeft EmptySeatMapError
    }
}