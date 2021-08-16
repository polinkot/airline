package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.usecase.flightId
import com.example.airline.flight.usecase.price
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CreateTicketRequestTest {

    @Test
    fun `successfully created`() {
        val flightId = flightId()
        val price = price()

        val result = CreateTicketRequest.from(flightId = flightId.value, price = price.value)

        result shouldBeRight CreateTicketRequest(flightId, price)
    }

    @Test
    fun `zero flightId`() {
        val result = CreateTicketRequest.from(flightId = 0, price = price().value)

        result shouldBeLeft InvalidTicketParameters("Non positive flight id")
    }

    @Test
    fun `negative flightId`() {
        val result = CreateTicketRequest.from(flightId = -100, price = price().value)

        result shouldBeLeft InvalidTicketParameters("Non positive flight id")
    }

    @Test
    fun `negative price`() {
        val result = CreateTicketRequest.from(flightId = flightId().value, price = BigDecimal.TEN.negate())

        result shouldBeLeft InvalidTicketParameters("Non positive price")
    }
}