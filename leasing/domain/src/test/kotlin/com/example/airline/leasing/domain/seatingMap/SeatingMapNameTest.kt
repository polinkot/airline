package com.example.airline.leasing.domain.seatingMap

import com.example.airline.leasing.domain.seatingmap.EmptySeatingMapNameError
import com.example.airline.leasing.domain.seatingmap.SeatingMapName
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SeatingMapNameTest {

    @Test
    fun `create seatingMapName - success`() {
        val name = "Name"
        val result = SeatingMapName.from(name)

        result shouldBeRight {
            it.value shouldBe name
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create seatingMapName - empty string`(input: String) {
        val result = SeatingMapName.from(input)
        result shouldBeLeft EmptySeatingMapNameError
    }
}