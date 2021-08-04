package com.example.airline.maintenance.usecase

import arrow.core.Either
import com.example.airline.common.types.common.Airport
import com.example.airline.maintenance.domain.FlightId
import java.time.Duration

interface ArriveFlight {
    fun execute(flightId: FlightId, arrivalAirport: Airport, duration: Duration): Either<ArriveFlightError, Unit>
}

sealed class ArriveFlightError(val message: String) {
    object FlightNotFound : ArriveFlightError("Flight not found")
    object InvalidFlightState : ArriveFlightError("Invalid flight state")
}