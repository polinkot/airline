package com.example.airline.maintenance.domain

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class FlightDurationTest {

    @Test
    fun `create flightDuration - success`() {
        val flightDuration = 100
        val result = FlightDuration.from(flightDuration)

        result shouldBeRight {
            it.value shouldBe flightDuration
        }
    }

    @Test
    fun `create flightDuration - zero`() {
        val result = FlightDuration.from(0)
        result shouldBeLeft NonPositiveFlightDurationError
    }

    @Test
    fun `create flightDuration - negative`() {
        val result = FlightDuration.from(-500)
        result shouldBeLeft NonPositiveFlightDurationError
    }
}