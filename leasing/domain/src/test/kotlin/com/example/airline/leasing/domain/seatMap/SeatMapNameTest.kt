package com.example.airline.leasing.domain.seatMap

import com.example.airline.leasing.domain.seatmap.EmptySeatMapNameError
import com.example.airline.leasing.domain.seatmap.SeatMapName
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SeatMapNameTest {

    @Test
    fun `create seatMapName - success`() {
        val name = "Name"
        val result = SeatMapName.from(name)

        result shouldBeRight {
            it.value shouldBe name
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create seatMapName - empty string`(input: String) {
        val result = SeatMapName.from(input)
        result shouldBeLeft EmptySeatMapNameError
    }
}