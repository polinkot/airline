package com.example.airline.common.types.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class Manufacturer internal constructor(val value: String) : ValueObject {
    companion object {
        fun from(name: String): Either<EmptyManufacturerError, Manufacturer> =
                if (name.isNotBlank()) {
                    Manufacturer(name).right()
                } else {
                    EmptyManufacturerError.left()
                }
    }
}

object EmptyManufacturerError : BusinessError