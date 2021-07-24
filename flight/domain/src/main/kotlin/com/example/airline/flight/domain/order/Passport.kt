package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.order.CreatePassportError.EmptyPassport

data class Passport internal constructor(
        val value: String
) : ValueObject {

    companion object {
        fun from(passport: String): Either<CreatePassportError, Passport> {
            return when {
                passport.isBlank() -> EmptyPassport.left()
                else -> Passport(passport).right()
            }
        }
    }
}

sealed class CreatePassportError {
    object EmptyPassport : CreatePassportError()
}
