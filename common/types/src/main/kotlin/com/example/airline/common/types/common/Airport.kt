package com.example.airline.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.common.CreateAirportError.EmptyAirportCodeError

data class Airport internal constructor(
        val code: String
) : ValueObject {
    companion object {
        fun from(code: String): Either<CreateAirportError, Airport> {
            return when {
                code.isBlank() -> EmptyAirportCodeError.left()
                else -> Airport(code).right()
            }
        }
    }
}

sealed class CreateAirportError {
    object EmptyAirportCodeError : CreateAirportError()
}