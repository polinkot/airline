package com.example.airline.leasing.usecase.aircraft

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

        val useCase = GetAircraftsUseCase(extractor)
        val result = useCase.execute()
        result shouldContainExactly listOf(
                AircraftInfo(
                        id = aircraft.id,
                        manufacturer = aircraft.manufacturer,
                        releaseDate = aircraft.releaseDate,
                        registrationNumber = aircraft.registrationNumber,
                        contractNumber = aircraft.contractNumber
                )
        )
    }
}