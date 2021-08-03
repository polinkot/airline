package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.rightIfNotNull
import com.example.airline.common.types.common.Airport
import com.example.airline.maintenance.domain.ArriveFlightError.FlightNotFound
import com.example.airline.maintenance.domain.ArriveFlightError.InvalidFlightState
import java.time.Duration

class ArriveFlightUseCase(
        private val flightExtractor: FlightExtractor,
        private val flightPersister: FlightPersister
) : ArriveFlight {

    override fun execute(flightId: FlightId, arrivalAirport: Airport, duration: Duration):
            Either<ArriveFlightError, Unit> {
        return flightExtractor.getById(flightId)
                .rightIfNotNull { FlightNotFound }
                .flatMap { flight ->
                    flight.arrive(arrivalAirport, duration).map {
                        flightPersister.save(flight)
                    }.mapLeft { InvalidFlightState }
                }
    }
}