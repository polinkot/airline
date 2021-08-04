package com.example.airline.maintenance.domain

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class FlightIdTest {

    @Test
    fun `create flightId - success`() {
        val flightId = Random.nextLong(20, 5000)
        val result = FlightId.from(flightId)

        result shouldBeRight {
            it.value shouldBe flightId
        }
    }

    @Test
    fun `create flightId - zero`() {
        val result = FlightId.from(0)
        result shouldBeLeft NonPositiveFlightIdError
    }

    @Test
    fun `create flightId - negative`() {
        val result = FlightId.from(-500)
        result shouldBeLeft NonPositiveFlightIdError
    }
}