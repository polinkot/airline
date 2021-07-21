package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.ticket.TicketId

interface TicketsAvailable {
    fun check(ticketIds: Set<TicketId>): Boolean
}