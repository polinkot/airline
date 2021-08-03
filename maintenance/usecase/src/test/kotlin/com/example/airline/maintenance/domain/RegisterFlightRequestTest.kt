package com.example.airline.maintenance.domain

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

class RegisterFlightRequestTest {

    @Test
    fun `successfully created`() {
        val id = flightId()
        val departureAirport = airport()

        val result = RegisterFlightRequest.from(
                id = id.value,
                departureAirport = departureAirport.value
        )

        result shouldBeRight RegisterFlightRequest(id, departureAirport)
    }

    @Test
    fun `invalid id`() {
        val id = -1L
        val departureAirport = airport()

        val result = RegisterFlightRequest.from(
                id = id,
                departureAirport = departureAirport.value
        )

        result shouldBeLeft InvalidFlightParameters("Non positive flight id")
    }

    @Test
    fun `invalid departureAirport`() {
        val id = flightId()
        val departureAirport = ""

        val result = RegisterFlightRequest.from(
                id = id.value,
                departureAirport = departureAirport
        )

        result shouldBeLeft InvalidFlightParameters("Empty departure airport")
    }
}