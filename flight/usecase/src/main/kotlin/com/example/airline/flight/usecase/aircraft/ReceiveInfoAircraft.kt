package com.example.airline.flight.usecase.aircraft

import arrow.core.Either

interface ReceiveInfoAircraft {
    fun execute(request: ReceiveInfoAircraftRequest): Either<ReceiveInfoAircraftUseCaseError, Unit>
}

data class ReceiveInfoAircraftRequest(val id: Long, val manufacturer: String, val seatsCount: Int)

sealed class ReceiveInfoAircraftUseCaseError(open val message: String) {
    object InvalidManufacturerError : ReceiveInfoAircraftUseCaseError("Empty manufacturer")
    object NoSeatsUseCaseError : ReceiveInfoAircraftUseCaseError("Empty seat map")
    object InvalidCount : ReceiveInfoAircraftUseCaseError("Negative value")
}