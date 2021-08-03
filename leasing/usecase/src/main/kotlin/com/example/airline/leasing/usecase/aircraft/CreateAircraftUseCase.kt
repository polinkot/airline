package com.example.airline.leasing.usecase.aircraft

import arrow.core.Either
import com.example.airline.leasing.domain.aircraft.Aircraft
import com.example.airline.leasing.domain.aircraft.AircraftId
import com.example.airline.leasing.domain.aircraft.AircraftIdGenerator
import com.example.airline.leasing.domain.aircraft.EmptySeatMapError

class CreateAircraftUseCase(
        private val aircraftPersister: AircraftPersister,
        private val idGenerator: AircraftIdGenerator
) : CreateAircraft {
    override fun execute(request: CreateAircraftRequest): Either<AircraftCreationUseCaseError, AircraftId> =
            Aircraft.create(
                    idGenerator = idGenerator,
                    manufacturer = request.manufacturer,
                    payload = request.payload,
                    releaseDate = request.releaseDate,
                    registrationNumber = request.registrationNumber,
                    contractNumber = request.contractNumber,
                    seats = request.seats
            ).mapLeft { e -> e.toError() }
                    .map { aircraft ->
                        aircraftPersister.save(aircraft)
                        aircraft.id
                    }
}

fun EmptySeatMapError.toError() = AircraftCreationUseCaseError.NoSeatsUseCaseError