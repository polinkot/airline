package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId

interface OrderPriceProvider {
    fun getPrice(tickets: Set<TicketId>): Price
}