package com.example.airline.flight.usecase.providers

import com.example.airline.flight.domain.order.TicketPriceProvider
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId
import com.example.airline.flight.usecase.ticket.TicketExtractor

class TicketPriceProviderImpl(private val extractor: TicketExtractor) : TicketPriceProvider {
    override fun getPrice(ticketId: TicketId): Price {
        val ticket = extractor.getById(ticketId)
        check(ticket != null) {
            "Ticket #$ticketId not found"
        }
        return ticket.price
    }
}