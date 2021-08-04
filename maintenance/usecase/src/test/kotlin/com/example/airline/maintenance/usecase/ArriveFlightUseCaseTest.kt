package com.example.airline.maintenance.usecase

import com.example.airline.maintenance.domain.*
import com.example.airline.maintenance.usecase.ArriveFlightError.FlightNotFound
import com.example.airline.maintenance.usecase.ArriveFlightError.InvalidFlightState
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

internal class ArriveFlightUseCaseTest {

    @Test
    fun `successfully arrived`() {
        val flight = flightReadyToArrive()

        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val persister = TestFlightPersister()

        val useCase = ArriveFlightUseCase(extractor, persister)
        val result = useCase.execute(flightId = flight.id, arrivalAirport = airport(), duration = duration())

        result.shouldBeRight()

        val arrivedFlight = persister[flight.id]
        arrivedFlight.shouldNotBeNull()
        arrivedFlight.popEvents() shouldContainExactly listOf(FlightArrivedDomainEvent(flight.id))
    }

    @Test
    fun `invalid state`() {
        val flight = flightNotReadyToArrive()
        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val persister = TestFlightPersister()

        val useCase = ArriveFlightUseCase(extractor, persister)
        val result = useCase.execute(flightId = flight.id, arrivalAirport = airport(), duration = duration())
        result shouldBeLeft InvalidFlightState
    }

    @Test
    fun `flight not found`() {
        val extractor = TestFlightExtractor()
        val persister = TestFlightPersister()

        val useCase = ArriveFlightUseCase(extractor, persister)
        val result = useCase.execute(flightId = flightId(), arrivalAirport = airport(), duration = duration())
        result shouldBeLeft FlightNotFound
    }
}