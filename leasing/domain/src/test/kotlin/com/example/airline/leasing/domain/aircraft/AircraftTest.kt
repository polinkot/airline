package com.example.airline.leasing.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class AircraftTest {

    val id = aircraftId()

    private val idGenerator = object : AircraftIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create aircraft - success`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = OffsetDateTime.now()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = seats()

        val result = Aircraft.create(idGenerator = idGenerator,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber,
                seats = seats)

        result shouldBeRight {
            it.id shouldBe id
            it.manufacturer shouldBe manufacturer
            it.payload shouldBe payload
            it.releaseDate shouldBe releaseDate
            it.registrationNumber shouldBe registrationNumber
            it.contractNumber shouldBe contractNumber
            it.seats shouldBe seats

            it.popEvents() shouldContainExactly listOf(AircraftCreatedDomainEvent(id))
        }
    }

    @Test
    fun `create aircraft - no seats`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = OffsetDateTime.now()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = emptySet<Seat>()

        val result = Aircraft.create(idGenerator = idGenerator,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber,
                seats = seats)

        result shouldBeLeft EmptySeatMapError
    }
}