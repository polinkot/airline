package com.example.airline.common.types.common

import com.example.airline.common.types.common.CreateAirportError.EmptyAirportCodeError
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class AirportTest {

    @Test
    fun `create airport - success`() {
        val code = "SVO"
        val result = Airport.from(code)

        result shouldBeRight {
            it.code shouldBe code
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create airport - empty string`(input: String) {
        val result = Airport.from(input)
        result shouldBeLeft EmptyAirportCodeError
    }
}