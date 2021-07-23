package com.example.airline.flight.domain.aircraft

import com.example.airline.flight.domain.aircraftId
import com.example.airline.flight.domain.count
import com.example.airline.flight.domain.manufacturer
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftTest {

    @Test
    fun `create aircraft - success`() {
        val id = aircraftId()
        val manufacturer = manufacturer()
        val seatsCount = count()

        val result = Aircraft.create(id = id,
                manufacturer = manufacturer,
                seatsCount = seatsCount)

        result.id shouldBe id
        result.manufacturer shouldBe manufacturer
        result.seatsCount shouldBe seatsCount

        result.popEvents() shouldContainExactly listOf(AircraftInfoReceivedDomainEvent(id))
    }
}