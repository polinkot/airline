package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId

interface TicketPriceProvider {
    fun getPrice(ticketId: TicketId): Price
}