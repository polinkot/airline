package com.example.airline.leasing.usecase.aircraft

import com.example.airline.leasing.domain.aircraft.AircraftIdGenerator
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CreateAircraftUseCaseTest {

    @Test
    fun `successfully added`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = seats()

        val persister = TestAircraftPersister()

        val result = CreateAircraftUseCase(
                aircraftPersister = persister,
                idGenerator = TestAircraftIdGenerator
        ).execute(
                CreateAircraftRequest(
                        manufacturer = manufacturer,
                        payload = payload,
                        releaseDate = releaseDate,
                        registrationNumber = registrationNumber,
                        contractNumber = contractNumber,
                        seats = seats
                )
        )

        val id = TestAircraftIdGenerator.id

        result shouldBeRight {
            it shouldBe id
        }

        val aircraft = persister[id]
        aircraft.shouldNotBeNull()

        aircraft.id shouldBe id
        aircraft.manufacturer shouldBe manufacturer
        aircraft.payload shouldBe payload
        aircraft.releaseDate shouldBe releaseDate
        aircraft.registrationNumber shouldBe registrationNumber
        aircraft.contractNumber shouldBe contractNumber
        aircraft.seats shouldBe seats
    }

    object TestAircraftIdGenerator : AircraftIdGenerator {
        val id = aircraftId()
        override fun generate() = id
    }
}