package com.example.airline.common.types.common

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ManufacturerTest {

    @Test
    fun `create manufacturer - success`() {
        val name = "Some string"
        val result = Manufacturer.from(name)

        result shouldBeRight {
            it.value shouldBe name
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create manufacturer - empty string`(input: String) {
        val result = Manufacturer.from(input)
        result shouldBeLeft EmptyManufacturerError
    }
}