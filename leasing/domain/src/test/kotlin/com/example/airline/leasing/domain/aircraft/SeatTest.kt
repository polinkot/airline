package com.example.airline.leasing.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SeatTest {

    @Test
    fun `create seat - success`() {
        val code = "5B"
        val result = Seat.from(code)

        result shouldBeRight {
            it.value shouldBe code
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create seat - empty string`(input: String) {
        val result = Seat.from(input)
        result shouldBeLeft EmptySeatError
    }
}