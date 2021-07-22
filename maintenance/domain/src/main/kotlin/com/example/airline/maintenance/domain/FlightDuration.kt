package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class FlightDuration internal constructor(val value: Int) : ValueObject {
    companion object {
        fun from(duration: Int): Either<NegativeFlightDurationError, FlightDuration> =
                if (duration > 0) {
                    FlightDuration(duration).right()
                } else {
                    NegativeFlightDurationError.left()
                }
    }
}

object NegativeFlightDurationError : BusinessError

