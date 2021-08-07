package com.example.airline.flight.usecase.aircraft

import com.example.airline.flight.domain.aircraft.AircraftInfoReceivedDomainEvent
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftUseCaseError.InvalidCount
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftUseCaseError.InvalidManufacturerError
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ReceiveInfoAircraftUseCaseTest {
    @Test
    fun `aircraft doesn't exist - aircraft created successfully`() {
        val aircraftId = aircraftId()
        val manufacturer = manufacturer()
        val seatsCount = count()

        val extractor = TestAircraftExtractor()
        val persister = TestAircraftPersister()
        val result = ReceiveInfoAircraftUseCase(extractor, persister)
                .execute(ReceiveInfoAircraftRequest(
                        id = aircraftId.value,
                        manufacturer = manufacturer.value,
                        seatsCount = seatsCount.value)
                )
        result.shouldBeRight()

        val aircraft = persister[aircraftId]
        aircraft.shouldNotBeNull()
        aircraft.id shouldBe aircraftId
        aircraft.manufacturer shouldBe manufacturer
        aircraft.seatsCount shouldBe seatsCount
        aircraft.popEvents() shouldContainExactly listOf(AircraftInfoReceivedDomainEvent(aircraftId))
    }

    @Test
    fun `aircraft exists - aircraft not created`() {
        val existingAircraft = aircraft()

        val persister = TestAircraftPersister()
        val extractor = TestAircraftExtractor().apply {
            this[existingAircraft.id] = existingAircraft
        }

        val result = ReceiveInfoAircraftUseCase(extractor, persister)
                .execute(ReceiveInfoAircraftRequest(
                        id = existingAircraft.id.value,
                        manufacturer = "",
                        seatsCount = 0)
                )
        result.shouldBeRight()

        val aircraft = persister[existingAircraft.id]
        aircraft.shouldBeNull()
    }

    @Test
    fun `manufacturer is empty`() {
        val result = ReceiveInfoAircraftUseCase(TestAircraftExtractor(), TestAircraftPersister())
                .execute(ReceiveInfoAircraftRequest(
                        id = aircraftId().value,
                        manufacturer = "",
                        seatsCount = count().value)
                )
        result shouldBeLeft InvalidManufacturerError
    }


    @Test
    fun `invalid count`() {
        val result = ReceiveInfoAircraftUseCase(TestAircraftExtractor(), TestAircraftPersister())
                .execute(ReceiveInfoAircraftRequest(
                        id = aircraftId().value,
                        manufacturer = manufacturer().value,
                        seatsCount = -1)
                )
        result shouldBeLeft InvalidCount
    }
}