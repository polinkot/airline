package com.example.airline.flight.usecase.ticket

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import arrow.core.right
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.CreatePriceError
import com.example.airline.flight.domain.ticket.Price
import java.math.BigDecimal

data class CreateTicketRequest internal constructor(
        val flightId: FlightId,
        val price: Price
) {
    companion object {
        fun from(
                flightId: Long,
                price: BigDecimal
        ): Either<InvalidTicketParameters, CreateTicketRequest> {
            return tupled(
                    if (flightId <= 0) {
                        InvalidTicketParameters("Non positive flight id").left()
                    } else {
                        FlightId(flightId).right()
                    },
                    Price.from(price).mapLeft { it.toError() }
            ).map { params ->
                CreateTicketRequest(params.a, params.b)
            }
        }
    }
}

data class InvalidTicketParameters(val message: String)

fun CreatePriceError.toError() = InvalidTicketParameters("Non positive price")
