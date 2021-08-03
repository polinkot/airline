package com.example.airline.leasing.usecase.aircraft

import arrow.core.Either
import arrow.core.extensions.either.apply.tupled
import arrow.core.left
import com.example.airline.common.types.common.EmptyManufacturerError
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.aircraft.*
import java.time.OffsetDateTime

@Suppress("LongParameterList")
data class CreateAircraftRequest internal constructor(
        val manufacturer: Manufacturer,
        val payload: AircraftPayload,
        val releaseDate: OffsetDateTime,
        val registrationNumber: AircraftRegistrationNumber,
        val contractNumber: AircraftContractNumber,
        val seats: Set<Seat>
) {
    companion object {
        fun from(
                manufacturer: String,
                payload: Int,
                releaseDate: OffsetDateTime,
                registrationNumber: String,
                contractNumber: String,
                seatParams: Set<String>
        ): Either<InvalidAircraftParameters, CreateAircraftRequest> {
            if (seatParams.isNullOrEmpty()) {
                return InvalidAircraftParameters("Empty seat map").left()
            }

            val seats = seatParams.map { it -> Seat.from(it).mapLeft { it.toError() } }
                    .mapNotNull { it.orNull() }.toSet()

            return tupled(
                    Manufacturer.from(manufacturer).mapLeft { it.toError() },
                    AircraftPayload.from(payload).mapLeft { it.toError() },
                    AircraftRegistrationNumber.from(registrationNumber).mapLeft { it.toError() },
                    AircraftContractNumber.from(contractNumber).mapLeft { it.toError() }
            ).map { params ->
                CreateAircraftRequest(params.a, params.b, releaseDate, params.c, params.d, seats)
            }
        }
    }
}

data class InvalidAircraftParameters(val message: String)

fun EmptyManufacturerError.toError() = InvalidAircraftParameters("Empty manufacturer")
fun NonPositiveAircraftPayloadError.toError() = InvalidAircraftParameters("Non positive payload")
fun EmptyAircraftRegistrationNumberError.toError() = InvalidAircraftParameters("Empty registration number")
fun EmptyAircraftContractNumberError.toError() = InvalidAircraftParameters("Empty contract number")

fun EmptySeatError.toError() = InvalidAircraftParameters("Empty seat")