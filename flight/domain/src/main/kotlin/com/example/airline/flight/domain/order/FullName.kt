package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.flight.domain.order.CreateFullNameError.EmptyFullName

data class FullName internal constructor(val value: String) : ValueObject {
    companion object {
        fun from(fullName: String): Either<CreateFullNameError, FullName> {
            return when {
                fullName.isBlank() -> EmptyFullName.left()
                else -> FullName(fullName).right()
            }
        }
    }
}

sealed class CreateFullNameError {
    object EmptyFullName : CreateFullNameError()
}