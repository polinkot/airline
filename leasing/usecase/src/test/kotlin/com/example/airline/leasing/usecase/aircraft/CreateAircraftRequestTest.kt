package com.example.airline.leasing.usecase.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

class CreateAircraftRequestTest {

    @Test
    fun `successfully created`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = seats()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer.value,
                payload = payload.value,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber.value,
                contractNumber = contractNumber.value,
                seatParams = seatParams(seats)
        )

        result shouldBeRight CreateAircraftRequest(manufacturer, payload, releaseDate,
                registrationNumber, contractNumber, seats)
    }

    @Test
    fun `invalid manufacturer`() {
        val result = CreateAircraftRequest.from(
                manufacturer = "",
                payload = payload().value,
                releaseDate = releaseDate(),
                registrationNumber = registrationNumber().value,
                contractNumber = contractNumber().value,
                seatParams = seatParams(seats())
        )

        result shouldBeLeft InvalidAircraftParameters("Empty manufacturer")
    }

    @Test
    fun `invalid payload`() {
        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer().value,
                payload = -5,
                releaseDate = releaseDate(),
                registrationNumber = registrationNumber().value,
                contractNumber = contractNumber().value,
                seatParams = seatParams(seats())
        )

        result shouldBeLeft InvalidAircraftParameters("Non positive payload")
    }

    @Test
    fun `invalid registrationNumber`() {
        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer().value,
                payload = payload().value,
                releaseDate = releaseDate(),
                registrationNumber = "",
                contractNumber = contractNumber().value,
                seatParams = seatParams(seats())
        )

        result shouldBeLeft InvalidAircraftParameters("Empty registration number")
    }

    @Test
    fun `invalid contractNumber`() {
        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer().value,
                payload = payload().value,
                releaseDate = releaseDate(),
                registrationNumber = registrationNumber().value,
                contractNumber = "",
                seatParams = seatParams(seats())
        )

        result shouldBeLeft InvalidAircraftParameters("Empty contract number")
    }

    @Test
    fun `no seats`() {
        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer().value,
                payload = payload().value,
                releaseDate = releaseDate(),
                registrationNumber = registrationNumber().value,
                contractNumber = contractNumber().value,
                seatParams = seatParams(emptySet())
        )

        result shouldBeLeft InvalidAircraftParameters("Empty seat map")
    }
}