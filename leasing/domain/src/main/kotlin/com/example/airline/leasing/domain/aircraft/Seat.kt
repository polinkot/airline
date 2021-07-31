package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

/**
 * Кресло в салоне. Ряд + место. Например 5B.
 */

data class Seat internal constructor(val code: String) : ValueObject {
    companion object {
        fun from(code: String): Either<EmptySeatError, Seat> =
                if (code.isNotBlank()) {
                    Seat(code).right()
                } else {
                    EmptySeatError.left()
                }
    }
}

object EmptySeatError : BusinessError