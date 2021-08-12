package com.example.airline.flight.usecase.rules

import com.example.airline.flight.usecase.TestFlightExtractor
import com.example.airline.flight.usecase.flight
import com.example.airline.flight.usecase.flightId
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class MoreThanHourTillDepartureImplTest {

    @Test
    fun `more than hour till departure`() {
        val flight = flight(OffsetDateTime.now().plusDays(1))
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = MoreThanHourTillDepartureImpl(extractor)

        val isMore = rule.check(flight.id)
        isMore.shouldBeTrue()
    }

    @Test
    fun `less than hour till departure`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val rule = MoreThanHourTillDepartureImpl(extractor)

        val isMore = rule.check(flight.id)
        isMore.shouldBeFalse()
    }

    @Test
    fun `when no flights`() {
        val extractor = TestFlightExtractor()
        val rule = MoreThanHourTillDepartureImpl(extractor)

        val isMore = rule.check(flightId())
        isMore.shouldBeFalse()
    }
}