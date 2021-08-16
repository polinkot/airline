package com.example.airline.leasing.usecase.aircraft

import arrow.core.Either
import com.example.airline.leasing.domain.aircraft.AircraftId

interface CreateAircraft {
    fun execute(request: CreateAircraftRequest): Either<AircraftCreationUseCaseError, AircraftId>
}

sealed class AircraftCreationUseCaseError(open val message: String) {
    object NoSeatsUseCaseError : AircraftCreationUseCaseError("Emty seat map")
}