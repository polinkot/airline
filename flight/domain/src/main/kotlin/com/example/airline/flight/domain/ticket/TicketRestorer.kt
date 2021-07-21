package com.example.airline.flight.domain.ticket

import com.example.airline.common.types.base.Version
import com.example.airline.flight.domain.flight.FlightId

object TicketRestorer {
    fun restore(
            id: TicketId,
            flightId: FlightId,
            price: Price,
            version: Version
    ): Ticket {
        return Ticket(
                id = id,
                flightId = flightId,
                price = price,
                version = version
        )
    }
}