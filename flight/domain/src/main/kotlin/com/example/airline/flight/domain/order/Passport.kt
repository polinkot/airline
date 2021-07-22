package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.order.CreatePassportError.EmptyString

data class Passport internal constructor(
        val value: String
) : ValueObject {

    companion object {
        fun from(value: String): Either<CreatePassportError, Passport> {
            return when {
                value.isBlank() -> EmptyString.left()
                else -> Passport(value).right()
            }
        }
    }
}

sealed class CreatePassportError {
    object EmptyString : CreatePassportError()
}
