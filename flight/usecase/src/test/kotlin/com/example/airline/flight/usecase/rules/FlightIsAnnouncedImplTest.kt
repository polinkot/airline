package com.example.airline.flight.usecase.rules

import com.example.airline.flight.usecase.TestFlightExtractor
import com.example.airline.flight.usecase.flight
import com.example.airline.flight.usecase.flightId
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test

internal class FlightIsAnnouncedImplTest {

    @Test
    fun `flight is announced`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = FlightIsAnnouncedImpl(extractor)

        val isAnnounced = rule.check(flight.id)
        isAnnounced.shouldBeTrue()
    }

    @Test
    fun `flight is not announced`() {
        val extractor = TestFlightExtractor()
        val rule = FlightIsAnnouncedImpl(extractor)

        val isAnnounced = rule.check(flightId())
        isAnnounced.shouldBeFalse()
    }
}