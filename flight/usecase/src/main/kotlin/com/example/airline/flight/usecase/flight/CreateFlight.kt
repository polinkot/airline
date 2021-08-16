package com.example.airline.flight.usecase.flight

import arrow.core.Either
import com.example.airline.flight.domain.flight.FlightId

interface CreateFlight {
    fun execute(request: CreateFlightRequest): Either<CreateFlightUseCaseError, FlightId>
}

sealed class CreateFlightUseCaseError(open val message: String) {
    object AircraftIsNotAvailableOnTimeUseCaseError : CreateFlightUseCaseError("Aircraft is not available on time")
    object AirportNotAllowFlightOnTimeUseCaseError : CreateFlightUseCaseError("Airport does not allow flight on time")
}