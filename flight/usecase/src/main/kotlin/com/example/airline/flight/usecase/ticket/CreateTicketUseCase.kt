package com.example.airline.flight.usecase.ticket

import arrow.core.Either
import com.example.airline.flight.domain.ticket.*
import com.example.airline.flight.domain.ticket.TicketCreationError.FlightIsNotAnnounced
import com.example.airline.flight.domain.ticket.TicketCreationError.NotEnoughTimeToDeparture
import com.example.airline.flight.usecase.ticket.CreateTicketUseCaseError.FlightIsNotAnnouncedUseCaseError
import com.example.airline.flight.usecase.ticket.CreateTicketUseCaseError.NotEnoughTimeToDepartureUseCaseError

class CreateTicketUseCase(
        private val persister: TicketPersister,
        private val idGenerator: TicketIdGenerator,
        private val flightIsAnnounced: FlightIsAnnounced,
        private val enoughTimeToDeparture: EnoughTimeToDeparture
) : CreateTicket {
    override fun execute(request: CreateTicketRequest): Either<CreateTicketUseCaseError, TicketId> =
            Ticket.create(
                    idGenerator = idGenerator,
                    flightId = request.flightId,
                    price = request.price,
                    flightIsAnnounced = flightIsAnnounced,
                    enoughTimeToDeparture = enoughTimeToDeparture
            ).mapLeft { e -> e.toError() }
                    .map { ticket ->
                        persister.save(ticket)
                        ticket.id
                    }
}

fun TicketCreationError.toError(): CreateTicketUseCaseError {
    return when (this) {
        FlightIsNotAnnounced -> FlightIsNotAnnouncedUseCaseError
        NotEnoughTimeToDeparture -> NotEnoughTimeToDepartureUseCaseError
    }
}