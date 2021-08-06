package com.example.airline.flight.usecase.aircraft

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.right
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.EmptyManufacturerError
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.common.types.common.NegativeValueError
import com.example.airline.flight.domain.aircraft.Aircraft
import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.aircraft.EmptySeatMapError

class ReceiveInfoAircraftUseCase(
        private val extractor: AircraftExtractor,
        private val persister: AircraftPersister

) : ReceiveInfoAircraft {
    override fun execute(request: ReceiveInfoAircraftRequest): Either<ReceiveInfoAircraftUseCaseError, Unit> {
        val aircraft = extractor.getById(AircraftId(request.id))
        return if (aircraft != null) {
            Unit.right()
        } else {
            createNewAircraft(request)
        }
    }

    private fun createNewAircraft(request: ReceiveInfoAircraftRequest): Either<ReceiveInfoAircraftUseCaseError, Unit> {
        return tupled(
                Manufacturer.from(request.manufacturer).mapLeft { it.toError() },
                Count.from(request.seatsCount).mapLeft { it.toError() }
        )
                .map { params ->
                    Aircraft.receiveInfo(id = AircraftId(request.id), params.a, params.b)
                            .mapLeft { it.toError() }
                            .map { aircraft ->
                                persister.save(aircraft)
                            }
                }
    }

    fun EmptyManufacturerError.toError() = ReceiveInfoAircraftUseCaseError.InvalidManufacturerError
    fun NegativeValueError.toError() = ReceiveInfoAircraftUseCaseError.InvalidCount
    fun EmptySeatMapError.toError() = ReceiveInfoAircraftUseCaseError.NoSeatsUseCaseError
}