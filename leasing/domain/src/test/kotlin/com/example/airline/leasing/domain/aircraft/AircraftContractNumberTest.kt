package com.example.airline.leasing.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AircraftContractNumberTest {

    @Test
    fun `create contractNumber - success`() {
        val number = "Some string"
        val result = AircraftContractNumber.from(number)

        result shouldBeRight {
            it.value shouldBe number
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create contractNumber - empty string`(input: String) {
        val result = AircraftContractNumber.from(input)
        result shouldBeLeft EmptyAircraftContractNumberError
    }
}