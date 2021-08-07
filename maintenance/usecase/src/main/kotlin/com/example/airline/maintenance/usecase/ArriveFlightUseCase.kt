package com.example.airline.maintenance.usecase

import arrow.core.Either
import arrow.core.getOrHandle
import arrow.core.left
import arrow.core.rightIfNotNull
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.CreateAirportError
import com.example.airline.maintenance.domain.ArriveFlightError
import com.example.airline.maintenance.domain.FlightId
import com.example.airline.maintenance.domain.NonPositiveFlightIdError
import com.example.airline.maintenance.usecase.ArriveFlightUseCaseError.*

@Suppress("ReturnCount")
class ArriveFlightUseCase(
        private val flightExtractor: FlightExtractor,
        private val flightPersister: FlightPersister
) : ArriveFlight {
    override fun execute(request: ArriveFlightRequest):
            Either<ArriveFlightUseCaseError, Unit> {
        val flightId = FlightId.from(request.id).getOrHandle { return it.toError().left() }
        return flightExtractor.getById(flightId)
                .rightIfNotNull { FlightNotFound }
                .map { flight ->
                    val airport = Airport.from(request.arrivalAirport).getOrHandle { return it.toError().left() }
                    flight.arrive(airport, request.duration).getOrHandle { return it.toError().left() }
                    flightPersister.save(flight)
                }
    }

    fun NonPositiveFlightIdError.toError() = FlightNotFound
    fun CreateAirportError.toError() = AirportNotFound
    fun ArriveFlightError.toError() = InvalidFlightState
}