package com.example.airline.flight.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.error.BusinessError
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.TicketCreationError.FlightIsNotAnnounced
import com.example.airline.flight.domain.ticket.TicketCreationError.NotEnoughTimeToDeparture

class Ticket internal constructor(
        id: TicketId,
        val flightId: FlightId,
        val price: Price,
        version: Version
) : AggregateRoot<TicketId>(id, version) {
    companion object {
        const val HOURS_TO_DEPARTURE = 5L

        fun create(
                idGenerator: TicketIdGenerator,
                flightId: FlightId,
                price: Price,
                flightIsAnnounced: FlightIsAnnounced,
                enoughTimeToDeparture: EnoughTimeToDeparture
        ): Either<TicketCreationError, Ticket> {
            if (!flightIsAnnounced.check(flightId)) {
                return FlightIsNotAnnounced.left()
            }

            if (!enoughTimeToDeparture.check(flightId, HOURS_TO_DEPARTURE)) {
                return NotEnoughTimeToDeparture.left()
            }

            return Ticket(
                    id = idGenerator.generate(),
                    flightId = flightId,
                    price = price,
                    version = Version.new()
            ).apply {
                addEvent(TicketCreatedDomainEvent(ticketId = this.id))
            }.right()
        }
    }
}

sealed class TicketCreationError : BusinessError {
    object FlightIsNotAnnounced : TicketCreationError()
    object NotEnoughTimeToDeparture : TicketCreationError()
}
