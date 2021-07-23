package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.seatingmap.EmptySeatingSeatError
import com.example.airline.leasing.domain.seatingmap.SeatingSeat
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SeatingSeatTest {

    @Test
    fun `create seatingSeat - success`() {
        val seat = "B"
        val result = SeatingSeat.from(seat)

        result shouldBeRight {
            it.value shouldBe seat
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create seatingSeat - empty string`(input: String) {
        val result = SeatingSeat.from(input)
        result shouldBeLeft EmptySeatingSeatError
    }
}