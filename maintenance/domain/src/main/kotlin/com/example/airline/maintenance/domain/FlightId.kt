package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class FlightId internal constructor(val value: Long) : ValueObject {
    companion object {
        fun from(id: Long): Either<NonPositiveFlightIdError, FlightId> =
                if (id > 0) {
                    FlightId(id).right()
                } else {
                    NonPositiveFlightIdError.left()
                }
    }
}

object NonPositiveFlightIdError : BusinessError