package com.example.airline.leasing.usecase.aircraft

import com.example.airline.leasing.domain.aircraft.Seat
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
        val manufacturer = ""
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = seats()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer,
                payload = payload.value,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber.value,
                contractNumber = contractNumber.value,
                seatParams = seatParams(seats)
        )

        result shouldBeLeft InvalidAircraftParameters("Empty manufacturer")
    }

    @Test
    fun `invalid payload`() {
        val manufacturer = manufacturer()
        val payload = -5
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = seats()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer.value,
                payload = payload,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber.value,
                contractNumber = contractNumber.value,
                seatParams = seatParams(seats)
        )

        result shouldBeLeft InvalidAircraftParameters("Non positive payload")
    }

    @Test
    fun `invalid registrationNumber`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = ""
        val contractNumber = contractNumber()
        val seats = seats()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer.value,
                payload = payload.value,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber,
                contractNumber = contractNumber.value,
                seatParams = seatParams(seats)
        )

        result shouldBeLeft InvalidAircraftParameters("Empty registration number")
    }

    @Test
    fun `invalid contractNumber`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = ""
        val seats = seats()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer.value,
                payload = payload.value,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber.value,
                contractNumber = contractNumber,
                seatParams = seatParams(seats)
        )

        result shouldBeLeft InvalidAircraftParameters("Empty contract number")
    }

    @Test
    fun `no seats`() {
        val manufacturer = manufacturer()
        val payload = payload()
        val releaseDate = releaseDate()
        val registrationNumber = registrationNumber()
        val contractNumber = contractNumber()
        val seats = emptySet<Seat>()

        val result = CreateAircraftRequest.from(
                manufacturer = manufacturer.value,
                payload = payload.value,
                releaseDate = releaseDate,
                registrationNumber = registrationNumber.value,
                contractNumber = contractNumber.value,
                seatParams = seatParams(seats)
        )

        result shouldBeLeft InvalidAircraftParameters("Empty seat map")
    }
}