package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.order.CreateFioError.EmptyString

data class Fio internal constructor(
        val value: String
) : ValueObject {

    companion object {
        fun from(value: String): Either<CreateFioError, Fio> {
            return when {
                value.isBlank() -> EmptyString.left()
                else -> Fio(value).right()
            }
        }
    }
}

sealed class CreateFioError {
    object EmptyString : CreateFioError()
}