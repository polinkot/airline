package com.example.airline.maintenance.usecase

import com.example.airline.maintenance.domain.FlightArrivedDomainEvent
import com.example.airline.maintenance.usecase.ArriveFlightUseCaseError.FlightNotFound
import com.example.airline.maintenance.usecase.ArriveFlightUseCaseError.InvalidFlightState
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

internal class ArriveFlightUseCaseTest {

    @Test
    fun `successfully arrived`() {
        val flight = flightReadyToArrive()
        val arrivalAirport = airport()
        val duration = duration()

        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val persister = TestFlightPersister()

        val result = ArriveFlightUseCase(extractor, persister).execute(ArriveFlightRequest(
                id = flight.id.value,
                arrivalAirport = arrivalAirport.value,
                duration = duration))

        result.shouldBeRight()

        val arrivedFlight = persister[flight.id]
        arrivedFlight.shouldNotBeNull()
        arrivedFlight.arrivalAirport = arrivalAirport
        arrivedFlight.duration = duration
        arrivedFlight.popEvents() shouldContainExactly listOf(FlightArrivedDomainEvent(flight.id))
    }

    @Test
    fun `invalid state`() {
        val flight = flightNotReadyToArrive()

        val extractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }
        val persister = TestFlightPersister()

        val result = ArriveFlightUseCase(extractor, persister).execute(ArriveFlightRequest(
                id = flight.id.value,
                arrivalAirport = airport().value,
                duration = duration()))
        result shouldBeLeft InvalidFlightState
    }

    @Test
    fun `flight not found`() {
        val extractor = TestFlightExtractor()
        val persister = TestFlightPersister()

        val result = ArriveFlightUseCase(extractor, persister).execute(ArriveFlightRequest(
                id = flightId().value,
                arrivalAirport = airport().value,
                duration = duration()))
        result shouldBeLeft FlightNotFound
    }
}