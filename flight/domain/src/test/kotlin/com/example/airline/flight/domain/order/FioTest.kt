package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.order.CreateFioError.EmptyFio
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class FioTest {

    @Test
    fun `create fio - success`() {
        val fio = "Some string"
        val result = Fio.from(fio)

        result shouldBeRight {
            it.value shouldBe fio
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create fio - empty string`(input: String) {
        val result = Fio.from(input)
        result shouldBeLeft EmptyFio
    }
}