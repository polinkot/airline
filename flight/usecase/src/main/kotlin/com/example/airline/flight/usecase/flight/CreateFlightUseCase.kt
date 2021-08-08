package com.example.airline.flight.usecase.flight

import arrow.core.Either
import com.example.airline.flight.domain.flight.*
import com.example.airline.flight.domain.flight.FlightAnnounceError.AircraftIsNotAvailableOnTime
import com.example.airline.flight.domain.flight.FlightAnnounceError.AirportNotAllowFlightOnTime

class CreateFlightUseCase(
        private val flightPersister: FlightPersister,
        private val idGenerator: FlightIdGenerator,
        private val aircraftIsAvailable: AircraftIsAvailableOnTime,
        private val airportAllowsFlight: AirportAllowsFlightOnTime
) : CreateFlight {
    override fun execute(request: CreateFlightRequest): Either<CreateFlightUseCaseError, FlightId> =
            Flight.create(
                    idGenerator = idGenerator,
                    departureAirport = request.departureAirport,
                    arrivalAirport = request.arrivalAirport,
                    flightDate = request.flightDate,
                    aircraftId = request.aircraftId,
                    aircraftIsAvailable = aircraftIsAvailable,
                    airportAllowsFlight = airportAllowsFlight
            ).mapLeft { e -> e.toError() }
                    .map { flight ->
                        flightPersister.save(flight)
                        flight.id
                    }
}

fun FlightAnnounceError.toError(): CreateFlightUseCaseError {
    return when (this) {
        AircraftIsNotAvailableOnTime -> CreateFlightUseCaseError.AircraftIsNotAvailableOnTimeUseCaseError
        AirportNotAllowFlightOnTime -> CreateFlightUseCaseError.AirportNotAllowFlightOnTimeUseCaseError
    }
}