package com.example.airline.flight.usecase.flight

import com.example.airline.flight.usecase.aircraftId
import com.example.airline.flight.usecase.airport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class CreateFlightRequestTest {

    @Test
    fun `successfully created`() {
        val departureAirport = airport()
        val arrivalAirport = airport()
        val flightDate = OffsetDateTime.now()
        val aircraftId = aircraftId()

        val result = CreateFlightRequest.from(
                departureAirport = departureAirport.value,
                arrivalAirport = arrivalAirport.value,
                flightDate = flightDate,
                aircraftId = aircraftId.value
        )

        result shouldBeRight CreateFlightRequest(departureAirport, arrivalAirport, flightDate, aircraftId)
    }

    @Test
    fun `invalid departureAirport`() {
        val result = CreateFlightRequest.from(
                departureAirport = "",
                arrivalAirport = airport().value,
                flightDate = OffsetDateTime.now(),
                aircraftId = aircraftId().value
        )

        result shouldBeLeft InvalidFlightParameters("Empty departure airport")
    }

    @Test
    fun `invalid arrivalAirport`() {
        val result = CreateFlightRequest.from(
                departureAirport = airport().value,
                arrivalAirport = "",
                flightDate = OffsetDateTime.now(),
                aircraftId = aircraftId().value
        )

        result shouldBeLeft InvalidFlightParameters("Empty arrival airport")
    }

    @Test
    fun `zero aircraftId`() {
        val result = CreateFlightRequest.from(
                departureAirport = airport().value,
                arrivalAirport = airport().value,
                flightDate = OffsetDateTime.now(),
                aircraftId = 0
        )

        result shouldBeLeft InvalidFlightParameters("Non positive aircraft id")
    }

    @Test
    fun `negative aircraftId`() {
        val result = CreateFlightRequest.from(
                departureAirport = airport().value,
                arrivalAirport = airport().value,
                flightDate = OffsetDateTime.now(),
                aircraftId = -100
        )

        result shouldBeLeft InvalidFlightParameters("Non positive aircraft id")
    }
}