package com.example.airline.leasing.domain.aircraft

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class AircraftRestorerTest {

    @Test
    fun `restore aircraft - success`() {
        val id = aircraftId()
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = OffsetDateTime.now()
        val contractNumber = contractNumber()
        val registrationNumber = registrationNumber()
        val seatingMapId = seatingMapId()
        val version = version()

        val aircraft = AircraftRestorer.restore(
                id = id,
                manufacturer = manufacturer,
                payload = payload,
                releaseDate = releaseDate,
                contractNumber = contractNumber,
                registrationNumber = registrationNumber,
                seatingMapId = seatingMapId,
                version = version
        )

        aircraft.id shouldBe id
        aircraft.manufacturer shouldBe manufacturer
        aircraft.payload shouldBe payload
        aircraft.releaseDate shouldBe releaseDate
        aircraft.contractNumber shouldBe contractNumber
        aircraft.registrationNumber shouldBe registrationNumber
        aircraft.seatingMapId shouldBe seatingMapId
        aircraft.version shouldBe version

        aircraft.popEvents().shouldBeEmpty()
    }
}