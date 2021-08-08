package com.example.airline.flight.usecase.flight

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.common.Airport
import com.example.airline.flight.domain.aircraft.AircraftId
import java.time.OffsetDateTime

data class CreateFlightRequest internal constructor(
        val departureAirport: Airport,
        val arrivalAirport: Airport,
        val flightDate: OffsetDateTime,
        val aircraftId: AircraftId
) {
    companion object {
        fun from(
                departureAirport: String,
                arrivalAirport: String,
                flightDate: OffsetDateTime,
                aircraftId: Long
        ): Either<InvalidFlightParameters, CreateFlightRequest> {
            return tupled(
                    Airport.from(departureAirport).mapLeft { InvalidFlightParameters("Empty departure airport") },
                    Airport.from(arrivalAirport).mapLeft { InvalidFlightParameters("Empty arrival airport") },
                    if (aircraftId <= 0) {
                        InvalidFlightParameters("Non positive aircraft id").left()
                    } else {
                        AircraftId(aircraftId).right()
                    }
            ).map { params ->
                CreateFlightRequest(params.a, params.b, flightDate, params.c)
            }
        }
    }
}

data class InvalidFlightParameters(val message: String)