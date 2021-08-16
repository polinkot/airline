package com.example.airline.flight.usecase.flight

import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.flight.AircraftIsAvailableOnTime
import com.example.airline.flight.domain.flight.AirportAllowsFlightOnTime
import com.example.airline.flight.domain.flight.FlightIdGenerator
import com.example.airline.flight.usecase.*
import com.example.airline.flight.usecase.flight.CreateFlightUseCaseError.AircraftIsNotAvailableOnTimeUseCaseError
import com.example.airline.flight.usecase.flight.CreateFlightUseCaseError.AirportNotAllowFlightOnTimeUseCaseError
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class CreateFlightUseCaseTest {

    @Test
    fun `successfully added`() {
        val departureAirport = airport()
        val arrivalAirport = airport()
        val flightDate = flightDate()
        val aircraftId = aircraftId()

        val persister = TestFlightPersister()

        val result = CreateFlightUseCase(
                flightPersister = persister,
                idGenerator = TestFlightIdGenerator,
                aircraftIsAvailable = AircraftIsAvailable,
                airportAllowsFlight = AirportAllowsFlight
        ).execute(
                CreateFlightRequest(
                        departureAirport = departureAirport,
                        arrivalAirport = arrivalAirport,
                        flightDate = flightDate,
                        aircraftId = aircraftId
                )
        )

        val id = TestFlightIdGenerator.id

        result shouldBeRight {
            it shouldBe id
        }

        val flight = persister[id]
        flight.shouldNotBeNull()

        flight.id shouldBe id
        flight.departureAirport shouldBe departureAirport
        flight.arrivalAirport shouldBe arrivalAirport
        flight.flightDate shouldBe flightDate
        flight.aircraftId shouldBe aircraftId
    }

    @Test
    fun `aircraft is not available`() {
        val persister = TestFlightPersister()

        val result = CreateFlightUseCase(
                flightPersister = persister,
                idGenerator = TestFlightIdGenerator,
                aircraftIsAvailable = AircraftIsNotAvailable,
                airportAllowsFlight = AirportAllowsFlight
        ).execute(
                CreateFlightRequest(
                        departureAirport = airport(),
                        arrivalAirport = airport(),
                        flightDate = flightDate(),
                        aircraftId = aircraftId()
                )
        )

        result shouldBeLeft AircraftIsNotAvailableOnTimeUseCaseError
        persister.shouldBeEmpty()
    }


    @Test
    fun `airport does not allow flight`() {
        val persister = TestFlightPersister()

        val result = CreateFlightUseCase(
                flightPersister = persister,
                idGenerator = TestFlightIdGenerator,
                aircraftIsAvailable = AircraftIsAvailable,
                airportAllowsFlight = AirportNotAllowsFlight
        ).execute(
                CreateFlightRequest(
                        departureAirport = airport(),
                        arrivalAirport = airport(),
                        flightDate = flightDate(),
                        aircraftId = aircraftId()
                )
        )

        result shouldBeLeft AirportNotAllowFlightOnTimeUseCaseError
        persister.shouldBeEmpty()
    }

    object TestFlightIdGenerator : FlightIdGenerator {
        val id = flightId()
        override fun generate() = id
    }

    object AircraftIsAvailable : AircraftIsAvailableOnTime {
        override fun check(aircraftId: AircraftId, datetime: OffsetDateTime) = true
    }

    object AircraftIsNotAvailable : AircraftIsAvailableOnTime {
        override fun check(aircraftId: AircraftId, datetime: OffsetDateTime) = false
    }

    object AirportAllowsFlight : AirportAllowsFlightOnTime {
        override fun check(datetime: OffsetDateTime) = true
    }

    object AirportNotAllowsFlight : AirportAllowsFlightOnTime {
        override fun check(datetime: OffsetDateTime) = false
    }
}