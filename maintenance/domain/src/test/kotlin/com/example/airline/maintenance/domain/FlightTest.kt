package com.example.airline.maintenance.domain

import com.example.airline.maintenance.domain.FlightState.ARRIVED
import com.example.airline.maintenance.domain.FlightState.REGISTERED
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.time.Duration

class FlightTest {

    @Test
    fun `register flight - success`() {
        val id = flightId()
        val departureAirport = airport()

        val result = Flight.register(id = id, departureAirport = departureAirport)

        result.id shouldBe id
        result.departureAirport shouldBe departureAirport
        result.arrivalAirport shouldBe null
        result.duration shouldBe Duration.ZERO
        result.state shouldBe REGISTERED

        result.popEvents() shouldContainExactly listOf(FlightRegisteredDomainEvent(id))
    }

    @Test
    fun `arrive - success`() {
        val flight = flight(state = REGISTERED)

        val newAirport = airport()
        val newDuration = duration()
        flight.arrive(newAirport, newDuration)

        flight.arrivalAirport shouldBe newAirport
        flight.duration shouldBe newDuration
        flight.state shouldBe ARRIVED
        flight.popEvents() shouldContainExactly listOf(FlightArrivedDomainEvent(flight.id))
    }

    @Test
    fun `arrive - already`() {
        val originalArrivalAirport = airport()
        val originalDuration = duration()
        val flight = flight(originalArrivalAirport, originalDuration, state = ARRIVED)

        flight.arrive(airport(), duration())

        flight.arrivalAirport shouldBe originalArrivalAirport
        flight.duration shouldBe originalDuration
        flight.state shouldBe ARRIVED
        flight.popEvents().shouldBeEmpty()
    }

    @ParameterizedTest
    @EnumSource(names = ["ARRIVED"])
    fun `arrive - invalid state`(state: FlightState) {
        val flight = flight(state = state)
        val originalArrivalAirport = flight.arrivalAirport
        val originalDuration = flight.duration

        flight.arrive(airport(), duration())

        flight.arrivalAirport shouldBe originalArrivalAirport
        flight.duration shouldBe originalDuration
        flight.state shouldBe state
        flight.popEvents().shouldBeEmpty()
    }
}