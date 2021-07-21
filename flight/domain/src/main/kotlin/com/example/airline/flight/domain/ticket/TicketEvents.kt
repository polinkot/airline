package com.example.airline.flight.domain.ticket

import com.example.airline.common.types.base.DomainEvent

data class TicketCreatedDomainEvent(val ticketId: TicketId) : DomainEvent()