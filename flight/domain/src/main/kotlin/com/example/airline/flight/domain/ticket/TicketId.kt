package com.example.airline.flight.domain.ticket

data class TicketId(val value: Long)

interface TicketIdGenerator {
    fun generate(): TicketId
}