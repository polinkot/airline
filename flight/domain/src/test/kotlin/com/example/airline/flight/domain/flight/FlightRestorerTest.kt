package com.example.airline.flight.domain.flight

import com.example.airline.flight.domain.aircraftId
import com.example.airline.flight.domain.airport
import com.example.airline.flight.domain.flightId
import com.example.airline.flight.domain.version
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class FlightRestorerTest {
    @Test
    fun `restore flight - success`() {
        val id = flightId()
        val departureAirport = airport()
        val arrivalAirport = airport()
        val flightDate = OffsetDateTime.now()
        val aircraftId = aircraftId()
        val version = version()

        val flight = FlightRestorer.restore(
                id = id,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                flightDate = flightDate,
                aircraftId = aircraftId,
                version = version
        )

        flight.id shouldBe id
        flight.departureAirport shouldBe departureAirport
        flight.arrivalAirport shouldBe arrivalAirport
        flight.flightDate shouldBe flightDate
        flight.aircraftId shouldBe aircraftId
        flight.version shouldBe version

        flight.popEvents().shouldBeEmpty()
    }
}