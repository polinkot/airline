package com.example.airline.flight.usecase.aircraft

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class GetAircraftsUseCaseTest {

    @Test
    fun `storage is empty`() {
        val extractor = TestAircraftExtractor()
        val useCase = GetAircraftsUseCase(extractor)
        val result = useCase.execute()
        result.shouldBeEmpty()
    }

    @Test
    fun `storage is not empty`() {
        val aircraft = aircraft()
        val extractor = TestAircraftExtractor().apply {
            this[aircraft.id] = aircraft
        }

        val result = GetAircraftsUseCase(extractor).execute()
        result shouldContainExactly listOf(
                AircraftInfo(
                        id = aircraft.id,
                        manufacturer = aircraft.manufacturer,
                        seatsCount = aircraft.seatsCount
                )
        )
    }
}