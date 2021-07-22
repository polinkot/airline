package com.example.airline.leasing.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AircraftRegistrationNumberTest {

    @Test
    fun `create registrationNumber - success`() {
        val number = "Some string"
        val result = AircraftRegistrationNumber.from(number)

        result shouldBeRight {
            it.value shouldBe number
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create registrationNumber - empty string`(input: String) {
        val result = AircraftRegistrationNumber.from(input)
        result shouldBeLeft EmptyAircraftRegistrationNumberError
    }
}