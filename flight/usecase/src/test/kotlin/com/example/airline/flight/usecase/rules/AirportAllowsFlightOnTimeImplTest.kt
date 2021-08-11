package com.example.airline.flight.usecase.rules

import com.example.airline.flight.usecase.TestAirportIntegrationService
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class AirportAllowsFlightOnTimeImplTest {

    @Test
    fun `airport allows flight on time`() {
        val integrationService = TestAirportIntegrationService(true)
        val rule = AirportAllowsFlightOnTimeImpl(integrationService)

        val allows = rule.check(OffsetDateTime.now())
        allows.shouldBeTrue()
    }

    @Test
    fun `airport does not allow flight on time`() {
        val integrationService = TestAirportIntegrationService(false)
        val rule = AirportAllowsFlightOnTimeImpl(integrationService)

        val allows = rule.check(OffsetDateTime.now())
        allows.shouldBeFalse()
    }
}