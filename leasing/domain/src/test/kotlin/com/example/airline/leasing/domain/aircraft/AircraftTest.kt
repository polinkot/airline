package com.example.airline.leasing.domain.aircraft

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
        val seatingMapId = seatingMapId()

        val result = Aircraft.create(idGenerator = idGenerator,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber,
                seatingMapId = seatingMapId)

        result.id shouldBe id
        result.manufacturer shouldBe manufacturer
        result.payload shouldBe payload
        result.releaseDate shouldBe releaseDate
        result.registrationNumber shouldBe registrationNumber
        result.contractNumber shouldBe contractNumber
        result.seatingMapId shouldBe seatingMapId

        result.popEvents() shouldContainExactly listOf(AircraftCreatedDomainEvent(id))
    }
}