package com.example.airline.leasing.domain.seatmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class Letter internal constructor(val value: String) : ValueObject {

    companion object {
        fun from(seat: String): Either<EmptySeatLetterError, Letter> =
                if (seat.isNotBlank()) {
                    Letter(seat).right()
                } else {
                    EmptySeatLetterError.left()
                }
    }
}

object EmptySeatLetterError : BusinessError