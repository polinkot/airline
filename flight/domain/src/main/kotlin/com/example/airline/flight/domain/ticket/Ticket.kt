package com.example.airline.flight.domain.ticket

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.error.BusinessError
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.TicketCreationError.FlightIsNotAnnounced
import com.example.airline.flight.domain.ticket.TicketCreationError.LessThanHourTillDeparture

class Ticket internal constructor(
        id: TicketId,
        val flightId: FlightId,
        val price: Price,
        version: Version
) : AggregateRoot<TicketId>(id, version) {
    companion object {
        fun create(
                idGenerator: TicketIdGenerator,
                flightId: FlightId,
                price: Price,
                flightIsAnnounced: FlightIsAnnounced,
                moreThanHourTillDeparture: MoreThanHourTillDeparture
        ): Either<TicketCreationError, Ticket> {
            if (!flightIsAnnounced.check(flightId)) {
                return FlightIsNotAnnounced.left()
            }

            if (!moreThanHourTillDeparture.check(flightId)) {
                return LessThanHourTillDeparture.left()
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
    object LessThanHourTillDeparture : TicketCreationError()
}
