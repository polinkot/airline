package com.example.airline.maintenance.domain

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class FlightRestorerTest {
    @Test
    fun `restore flight - success`() {
        val id = flightId()
        val departureAirport = airport()
        val arrivalAirport = airport()
        val duration = duration()
        val state = FlightState.ARRIVED
        val version = version()

        val flight = FlightRestorer.restore(
                id = id,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                duration = duration,
                state = state,
                version = version
        )

        flight.id shouldBe id
        flight.departureAirport shouldBe departureAirport
        flight.arrivalAirport shouldBe arrivalAirport
        flight.duration shouldBe duration
        flight.state shouldBe state
        flight.version shouldBe version

        flight.popEvents().shouldBeEmpty()
    }
}