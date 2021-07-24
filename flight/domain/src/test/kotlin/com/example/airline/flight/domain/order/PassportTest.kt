package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.order.CreatePassportError.EmptyPassport
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class PassportTest {

    @Test
    fun `create passport - success`() {
        val passport = "Some string"
        val result = Passport.from(passport)

        result shouldBeRight {
            it.value shouldBe passport
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create passport - empty string`(input: String) {
        val result = Passport.from(input)
        result shouldBeLeft EmptyPassport
    }
}