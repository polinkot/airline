package com.example.airline.leasing.domain.seatingmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class SeatingMapName internal constructor(val value: String) : ValueObject {

    companion object {
        fun from(name: String): Either<EmptySeatingMapNameError, SeatingMapName> =
                if (name.isNotBlank()) {
                    SeatingMapName(name).right()
                } else {
                    EmptySeatingMapNameError.left()
                }
    }
}

object EmptySeatingMapNameError : BusinessError