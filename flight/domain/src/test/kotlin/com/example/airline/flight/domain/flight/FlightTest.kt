package com.example.airline.flight.domain.flight

import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.aircraftId
import com.example.airline.flight.domain.airport
import com.example.airline.flight.domain.flight.FlightAnnounceError.AircraftIsNotAvailableOnTime
import com.example.airline.flight.domain.flight.FlightAnnounceError.AirportNotAllowFlightOnTime
import com.example.airline.flight.domain.flightId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class FlightTest {
    val id = flightId()

    private val idGenerator = object : FlightIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create flight - success`() {
        val departureAirport = airport()
        val arrivalAirport = airport()
        val flightDate = OffsetDateTime.now()
        val aircraftId = aircraftId()
        val aircraftIsAvailableOnTime = TestAircraftIsAvailableOnTime(true)
        val airportAllowsFlightOnTime = TestAirportAllowsFlightOnTime(true)

        val result = Flight.create(idGenerator = idGenerator,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                flightDate = flightDate,
                aircraftId = aircraftId,
                aircraftIsAvailableOnTime = aircraftIsAvailableOnTime,
                airportAllowsFlightOnTime = airportAllowsFlightOnTime
        )

        result shouldBeRight {
            it.id shouldBe id
            it.departureAirport shouldBe departureAirport
            it.arrivalAirport shouldBe arrivalAirport
            it.flightDate shouldBe flightDate
            it.aircraftId shouldBe aircraftId

            it.popEvents() shouldContainExactly listOf(FlightAnnouncedDomainEvent(id))
        }
    }

    @Test
    fun `create flight - aircraft is not available on time`() {
        val result = Flight.create(idGenerator = idGenerator,
                departureAirport = airport(),
                arrivalAirport = airport(),
                flightDate = OffsetDateTime.now(),
                aircraftId = aircraftId(),
                aircraftIsAvailableOnTime = TestAircraftIsAvailableOnTime(false),
                airportAllowsFlightOnTime = TestAirportAllowsFlightOnTime(true)
        )

        result shouldBeLeft AircraftIsNotAvailableOnTime
    }

    @Test
    fun `create flight - airport doesnt allow flight on time`() {
        val result = Flight.create(idGenerator = idGenerator,
                departureAirport = airport(),
                arrivalAirport = airport(),
                flightDate = OffsetDateTime.now(),
                aircraftId = aircraftId(),
                aircraftIsAvailableOnTime = TestAircraftIsAvailableOnTime(true),
                airportAllowsFlightOnTime = TestAirportAllowsFlightOnTime(false)
        )

        result shouldBeLeft AirportNotAllowFlightOnTime
    }

    class TestAircraftIsAvailableOnTime(val isAvailable: Boolean) : AircraftIsAvailableOnTime {
        override fun check(aircraftId: AircraftId, datetime: OffsetDateTime): Boolean {
            return isAvailable
        }
    }

    class TestAirportAllowsFlightOnTime(val allows: Boolean) : AirportAllowsFlightOnTime {
        override fun check(datetime: OffsetDateTime): Boolean {
            return allows
        }
    }
}