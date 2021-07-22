package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class AircraftPayload internal constructor(val value: Int) : ValueObject {
    companion object {
        fun from(payload: Int): Either<NonPositiveAircraftPayloadError, AircraftPayload> =
                if (payload > 0) {
                    AircraftPayload(payload).right()
                } else {
                    NonPositiveAircraftPayloadError.left()
                }
    }
}

object NonPositiveAircraftPayloadError : BusinessError


