package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.order.CreateFioError.EmptyFio

data class Fio internal constructor(val value: String) : ValueObject {
    companion object {
        fun from(fio: String): Either<CreateFioError, Fio> {
            return when {
                fio.isBlank() -> EmptyFio.left()
                else -> Fio(fio).right()
            }
        }
    }
}

sealed class CreateFioError {
    object EmptyFio : CreateFioError()
}