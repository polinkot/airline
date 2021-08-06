package com.example.airline.common.types.common

import com.example.airline.common.types.common.CreateEmailError.EmptyEmailError
import com.example.airline.common.types.common.CreateEmailError.WrongFormatEmaiError
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class EmailTest {

    @Test
    fun `create email - success`() {
        val email = "Some@email.com"
        val result = Email.from(email)

        result shouldBeRight {
            it.value shouldBe email
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `create email - empty string`(input: String) {
        val result = Email.from(input)
        result shouldBeLeft EmptyEmailError
    }

    @ParameterizedTest
    @ValueSource(strings = ["wrongformat.com", "wrongformat", "wrong@format", ".wrong"])
    fun `create email - wrong format`(input: String) {
        val result = Email.from(input)
        result shouldBeLeft WrongFormatEmaiError
    }
}