package com.example.airline.flight.usecase.ticket

import arrow.core.Either
import com.example.airline.flight.domain.ticket.TicketId

interface CreateTicket {
    fun execute(request: CreateTicketRequest): Either<CreateTicketUseCaseError, TicketId>
}

sealed class CreateTicketUseCaseError(open val message: String) {
    object FlightIsNotAnnouncedUseCaseError : CreateTicketUseCaseError("Flight is not announced")
    object LessThanHourTillDepartureUseCaseError : CreateTicketUseCaseError("Less than hour till departure")
}