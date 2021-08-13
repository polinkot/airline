package com.example.airline.flight.usecase.rules

import com.example.airline.flight.usecase.TestFlightExtractor
import com.example.airline.flight.usecase.flight
import com.example.airline.flight.usecase.flightId
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class EnoughTimeToDepartureImplTest {

    @Test
    fun `enough time to departure`() {
        val flight = flight(OffsetDateTime.now().plusDays(1))
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = EnoughTimeToDepartureImpl(extractor)

        val isEnough = rule.check(flight.id, 1L)
        isEnough.shouldBeTrue()
    }

    @Test
    fun `not enough time to departure`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = EnoughTimeToDepartureImpl(extractor)

        val isEnough = rule.check(flight.id, 1L)
        isEnough.shouldBeFalse()
    }

    @Test
    fun `when no flights`() {
        val extractor = TestFlightExtractor()
        val rule = EnoughTimeToDepartureImpl(extractor)

        val isEnough = rule.check(flightId(), 1L)
        isEnough.shouldBeFalse()
    }
}