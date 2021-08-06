package com.example.airline.flight.domain.order

import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.ticket.TicketId

/**
 * Билет плюс данные пассажира - ФИО, паспорт.
 */
data class OrderItem internal constructor(
        val ticketId: TicketId,
        val fullName: FullName,
        val passport: Passport
) : ValueObject {
    companion object {
        fun from(ticketId: TicketId, fullName: FullName, passport: Passport): OrderItem {
            return OrderItem(ticketId, fullName, passport)
        }
    }
}