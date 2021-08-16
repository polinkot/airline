package com.example.airline.maintenance.usecase

import arrow.core.Either
import java.time.Duration

interface ArriveFlight {
    fun execute(request: ArriveFlightRequest): Either<ArriveFlightUseCaseError, Unit>
}

data class ArriveFlightRequest(val id: Long, val arrivalAirport: String, val duration: Duration)

sealed class ArriveFlightUseCaseError(val message: String) {
    object FlightNotFound : ArriveFlightUseCaseError("Flight not found")
    object AirportNotFound : ArriveFlightUseCaseError("Airport not found")
    object InvalidFlightState : ArriveFlightUseCaseError("Invalid flight state")
}