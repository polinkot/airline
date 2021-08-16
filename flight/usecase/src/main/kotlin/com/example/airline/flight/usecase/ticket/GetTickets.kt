package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId

interface GetTickets {
    fun execute(): List<TicketInfo>
}

data class TicketInfo(
        val id: TicketId,
        val price: Price
)