package com.example.airline.flight.domain.aircraft

import com.example.airline.flight.domain.aircraftId
import com.example.airline.flight.domain.count
import com.example.airline.flight.domain.manufacturer
import com.example.airline.flight.domain.version
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val id = aircraftId()
        val manufacturer = manufacturer()
        val seatsCount = count()
        val version = version()

        val aircraft = AircraftRestorer.restore(
                id = id,
                manufacturer = manufacturer,
                seatsCount = seatsCount,
                version = version
        )

        aircraft.id shouldBe id
        aircraft.manufacturer shouldBe manufacturer
        aircraft.seatsCount shouldBe seatsCount
        aircraft.version shouldBe version

        aircraft.popEvents().shouldBeEmpty()
    }
}