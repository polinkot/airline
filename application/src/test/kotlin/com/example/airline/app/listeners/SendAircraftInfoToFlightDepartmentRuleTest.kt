package com.example.airline.app.listeners

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.app.TestAircraftExtractor
import com.example.airline.app.aircraft
import com.example.airline.app.aircraftId
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraft
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftRequest
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftUseCaseError
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftUseCaseError.NoSeatsUseCaseError
import com.example.airline.leasing.domain.aircraft.AircraftCreatedDomainEvent
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SendAircraftInfoToFlightDepartmentRuleTest {

    @Test
    fun `aircraft info successfully sent`() {
        val aircraft = aircraft()

        val aircraftExtractor = TestAircraftExtractor().apply {
            this[aircraft.id] = aircraft
        }

        val useCase = TestReceiveInfoAircraft(Unit.right())

        val rule = SendAircraftInfoToFlightDepartmentRule(
                leasingAircraftExtractor = aircraftExtractor,
                receiveInfoAircraft = useCase
        )

        val event = AircraftCreatedDomainEvent(aircraft.id)
        rule.handle(event)

        useCase.request.id shouldBe aircraft.id.value
        useCase.request.manufacturer shouldBe aircraft.manufacturer.value
        useCase.request.seatsCount shouldBe aircraft.seats.size
    }

    @Test
    fun `aircraft not found`() {
        val aircraftExtractor = TestAircraftExtractor()

        val useCase = TestReceiveInfoAircraft(Unit.right())

        val rule = SendAircraftInfoToFlightDepartmentRule(
                leasingAircraftExtractor = aircraftExtractor,
                receiveInfoAircraft = useCase
        )

        val event = AircraftCreatedDomainEvent(aircraftId())

        shouldThrow<IllegalStateException> {
            rule.handle(event)
        }

        useCase.verifyZeroInteraction()
    }

    @Test
    fun `aircraft creation error`() {
        val aircraft = aircraft()

        val aircraftExtractor = TestAircraftExtractor().apply {
            this[aircraft.id] = aircraft
        }

        val useCase = TestReceiveInfoAircraft(NoSeatsUseCaseError.left())

        val rule = SendAircraftInfoToFlightDepartmentRule(
                leasingAircraftExtractor = aircraftExtractor,
                receiveInfoAircraft = useCase
        )

        val event = AircraftCreatedDomainEvent(aircraft.id)

        shouldThrow<IllegalStateException> {
            rule.handle(event)
        }

        useCase.request.id shouldBe aircraft.id.value
        useCase.request.manufacturer shouldBe aircraft.manufacturer.value
        useCase.request.seatsCount shouldBe aircraft.seats.size
    }

    private class TestReceiveInfoAircraft(val response: Either<ReceiveInfoAircraftUseCaseError, Unit>)
        : ReceiveInfoAircraft {
        lateinit var request: ReceiveInfoAircraftRequest

        override fun execute(request: ReceiveInfoAircraftRequest): Either<ReceiveInfoAircraftUseCaseError, Unit> {
            this.request = request
            return response
        }

        fun verifyZeroInteraction() {
            ::request.isInitialized.shouldBeFalse()
        }
    }
}