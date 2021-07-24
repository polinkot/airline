package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.seatmap.EmptySeatLetterError
import com.example.airline.leasing.domain.seatmap.Letter
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class LetterTest {

    @Test
    fun `create seatLetter - success`() {
        val seat = "B"
        val result = Letter.from(seat)

        result shouldBeRight {
            it.value shouldBe seat
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create seatLetter - empty string`(input: String) {
        val result = Letter.from(input)
        result shouldBeLeft EmptySeatLetterError
    }
}