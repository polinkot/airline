package com.example.airline.flight.usecase.rules

import com.example.airline.flight.usecase.TestFlightExtractor
import com.example.airline.flight.usecase.aircraftId
import com.example.airline.flight.usecase.flight
import com.example.airline.flight.usecase.flightDate
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class AircraftIsAvailableOnTimeImplTest {

    @Test
    fun `aircraft is available on time`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = AircraftIsAvailableOnTimeImpl(extractor)

        val isAvailable = rule.check(flight.aircraftId, OffsetDateTime.now().plusDays(1))
        isAvailable.shouldBeTrue()
    }

    @Test
    fun `aircraft is not available on time`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = AircraftIsAvailableOnTimeImpl(extractor)

        val isAvailable = rule.check(flight.aircraftId, flight.flightDate)
        isAvailable.shouldBeFalse()
    }

    @Test
    fun `aircraft is available when no flights`() {
        val extractor = TestFlightExtractor()
        val rule = AircraftIsAvailableOnTimeImpl(extractor)

        val isAvailable = rule.check(aircraftId(), flightDate())
        isAvailable.shouldBeTrue()
    }
}