package com.example.airline.app.listeners

import com.example.airline.app.TestFlightExtractor
import com.example.airline.app.flight
import com.example.airline.app.maintenanceFlightId
import com.example.airline.flight.domain.flight.FlightAnnouncedDomainEvent
import com.example.airline.maintenance.domain.FlightId
import com.example.airline.maintenance.usecase.RegisterFlight
import com.example.airline.maintenance.usecase.RegisterFlightRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SendFlightInfoToMaintenanceDepartmentRuleTest {

    @Test
    fun `flight info successfully sent`() {
        val flight = flight()

        val flightExtractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }

        val flightId = maintenanceFlightId(flight.id.value)

        val useCase = TestRegisterFlight(flightId)

        val rule = SendFlightInfoToMaintenanceDepartmentRule(
                flightExtractor = flightExtractor,
                registerFlight = useCase
        )

        val event = FlightAnnouncedDomainEvent(flight.id)
        rule.handle(event)

        useCase.request.id.value shouldBe flight.id.value
        useCase.request.departureAirport.value shouldBe flight.departureAirport.value
    }

    @Test
    fun `flight not found`() {
        val flight = flight()

        val flightExtractor = TestFlightExtractor()

        val useCase = TestRegisterFlight(maintenanceFlightId(flight.id.value))

        val rule = SendFlightInfoToMaintenanceDepartmentRule(
                flightExtractor = flightExtractor,
                registerFlight = useCase
        )

        val event = FlightAnnouncedDomainEvent(flight.id)
        shouldThrow<IllegalStateException> {
            rule.handle(event)
        }

        useCase.verifyZeroInteraction()
    }

    private class TestRegisterFlight(val response: FlightId) : RegisterFlight {
        lateinit var request: RegisterFlightRequest

        override fun execute(request: RegisterFlightRequest): FlightId {
            this.request = request
            return response
        }

        fun verifyZeroInteraction() {
            ::request.isInitialized.shouldBeFalse()
        }
    }
}