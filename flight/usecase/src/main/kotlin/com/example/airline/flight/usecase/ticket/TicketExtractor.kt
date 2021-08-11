package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.domain.ticket.Ticket
import com.example.airline.flight.domain.ticket.TicketId

interface TicketExtractor {

    fun getById(id: TicketId): Ticket?

    fun getAll(): List<Ticket>
}