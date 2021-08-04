package com.example.airline.maintenance.usecase

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class GetFlightsUseCaseTest {

    @Test
    fun `storage is empty`() {
        val extractor = TestFlightExtractor()
        val useCase = GetFlightsUseCase(extractor)
        val result = useCase.execute()
        result.shouldBeEmpty()
    }

    @Test
    fun `storage is not empty`() {
        val flight = flight()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }

        val useCase = GetFlightsUseCase(extractor)
        val result = useCase.execute()
        result shouldContainExactly listOf(
                FlightInfo(
                        id = flight.id,
                        departureAirport = flight.departureAirport,
                        arrivalAirport = flight.arrivalAirport,
                        state = flight.state
                )
        )
    }
}