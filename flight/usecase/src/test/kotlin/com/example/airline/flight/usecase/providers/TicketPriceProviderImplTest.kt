package com.example.airline.flight.usecase.providers

import com.example.airline.flight.usecase.TestTicketExtractor
import com.example.airline.flight.usecase.ticket
import com.example.airline.flight.usecase.ticketId
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TicketPriceProviderImplTest {

    @Test
    fun `price has been provided`() {
        val ticket = ticket()

        val extractor = TestTicketExtractor().apply {
            this[ticket.id] = ticket
        }

        val provider = TicketPriceProviderImpl(extractor)
        val result = provider.getPrice(ticket.id)
        result shouldBe ticket.price
    }

    @Test
    fun `ticket not found`() {
        val extractor = TestTicketExtractor()
        val provider = TicketPriceProviderImpl(extractor)
        shouldThrow<IllegalStateException> {
            provider.getPrice(ticketId())
        }
    }
}
