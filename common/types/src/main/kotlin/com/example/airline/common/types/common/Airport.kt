package com.example.airline.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.common.CreateAirportError.EmptyCityError
import com.example.airline.common.types.common.CreateAirportError.EmptyCodeError

data class Airport internal constructor(
        val city: String,
        val code: String
) : ValueObject {

    companion object {
        fun from(city: String, code: String): Either<CreateAirportError, Airport> {
            return when {
                city.isBlank() -> EmptyCityError.left()
                code.isBlank() -> EmptyCodeError.left()
                else -> Airport(city, code).right()
            }
        }
    }
}

sealed class CreateAirportError {
    object EmptyCityError : CreateAirportError()
    object EmptyCodeError : CreateAirportError()
}