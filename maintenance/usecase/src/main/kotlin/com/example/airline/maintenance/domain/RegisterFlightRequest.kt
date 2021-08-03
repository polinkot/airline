package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.CreateAirportError

data class RegisterFlightRequest internal constructor(
        val id: FlightId,
        val departureAirport: Airport
) {
    companion object {
        fun from(id: Long, departureAirport: String): Either<InvalidFlightParameters, RegisterFlightRequest> {
            return tupled(
                    FlightId.from(id).mapLeft { it.toError() },
                    Airport.from(departureAirport).mapLeft { it.toError() }
            ).map { params ->
                RegisterFlightRequest(params.a, params.b)
            }
        }
    }
}

data class InvalidFlightParameters(val message: String)

fun NonPositiveFlightIdError.toError() = InvalidFlightParameters("Non positive flight id")
fun CreateAirportError.toError() = InvalidFlightParameters("Empty departure airport")
