package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.ticket.TicketId

/**
 * Билет плюс данные пассажира - ФИО, паспорт.
 */
class OrderItem constructor(
        val ticketId: TicketId,
        val fullName: FullName,
        val passport: Passport
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        if (ticketId != other.ticketId) return false

        return true
    }

    override fun hashCode(): Int {
        return ticketId.hashCode()
    }
}