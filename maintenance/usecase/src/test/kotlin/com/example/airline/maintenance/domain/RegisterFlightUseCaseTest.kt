package com.example.airline.maintenance.domain

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class RegisterFlightUseCaseTest {

    @Test
    fun `successfully registered`() {
        val id = flightId()
        val departureAirport = airport()

        val persister = TestFlightPersister()

        RegisterFlightUseCase(
                flightPersister = persister
        ).execute(
                RegisterFlightRequest(
                        id = id,
                        departureAirport = departureAirport
                )
        )

        val flight = persister[id]
        flight.shouldNotBeNull()

        flight.id shouldBe id
        flight.departureAirport shouldBe departureAirport
    }
}