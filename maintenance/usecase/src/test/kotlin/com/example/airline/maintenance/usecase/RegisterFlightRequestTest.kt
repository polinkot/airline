package com.example.airline.maintenance.usecase

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

class RegisterFlightRequestTest {

    @Test
    fun `successfully created`() {
        val id = flightId()
        val departureAirport = airport()

        val result = RegisterFlightRequest.from(id = id.value, departureAirport = departureAirport.value)

        result shouldBeRight RegisterFlightRequest(id, departureAirport)
    }

    @Test
    fun `invalid id`() {
        val result = RegisterFlightRequest.from(id = -1L, departureAirport = airport().value)

        result shouldBeLeft InvalidFlightParameters("Non positive flight id")
    }

    @Test
    fun `invalid departureAirport`() {
        val result = RegisterFlightRequest.from(id = flightId().value, departureAirport = "")

        result shouldBeLeft InvalidFlightParameters("Empty departure airport")
    }
}