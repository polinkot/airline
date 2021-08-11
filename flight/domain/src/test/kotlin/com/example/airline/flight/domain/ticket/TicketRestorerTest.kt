package com.example.airline.flight.domain.ticket

import com.example.airline.flight.domain.flightId
import com.example.airline.flight.domain.price
import com.example.airline.flight.domain.ticketId
import com.example.airline.flight.domain.version
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TicketRestorerTest {
    @Test
    fun `restore ticket - success`() {
        val id = ticketId()
        val flightId = flightId()
        val price = price()
        val version = version()

        val ticket = TicketRestorer.restore(
                id = id,
                flightId = flightId,
                price = price,
                version = version
        )

        ticket.id shouldBe id
        ticket.flightId shouldBe flightId
        ticket.price shouldBe price
        ticket.version shouldBe version

        ticket.popEvents().shouldBeEmpty()
    }
}