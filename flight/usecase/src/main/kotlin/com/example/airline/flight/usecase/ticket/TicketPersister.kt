package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.domain.ticket.Ticket

interface TicketPersister {
    fun save(ticket: Ticket)
}