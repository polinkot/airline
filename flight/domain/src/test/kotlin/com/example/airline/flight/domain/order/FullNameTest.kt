package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.order.CreateFullNameError.EmptyFullName
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FullNameTest {

    @Test
    fun `create fullName - success`() {
        val fullName = "Some string"
        val result = FullName.from(fullName)

        result shouldBeRight {
            it.value shouldBe fullName
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create fullName - empty string`(input: String) {
        val result = FullName.from(input)
        result shouldBeLeft EmptyFullName
    }
}