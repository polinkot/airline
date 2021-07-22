package com.example.airline.leasing.domain.aircraft

import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class ArtifactPayloadTest {

    @Test
    fun `create payload - success`() {
        val payload = 1000
        val result = AircraftPayload.from(payload)

        result shouldBeRight {
            it.value shouldBe payload
        }
    }

    @Test
    fun `create payload - zero`() {
        val result = AircraftPayload.from(0)
        result shouldBeLeft NonPositiveAircraftPayloadError
    }

    @Test
    fun `create payload - negative`() {
        val result = AircraftPayload.from(-500)
        result shouldBeLeft NonPositiveAircraftPayloadError
    }
}