package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class AircraftPayload internal constructor(val value: Int) : ValueObject {
    companion object {
        fun from(number: Int): Either<NegativeAircraftPayloadError, AircraftPayload> =
                if (number > 0) {
                    AircraftPayload(number).right()
                } else {
                    NegativeAircraftPayloadError.left()
                }
    }
}

object NegativeAircraftPayloadError : BusinessError


