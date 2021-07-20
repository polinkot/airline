package com.example.airline.leasing.domain.seatingmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class SeatingSeat internal constructor(val value: String) : ValueObject {

    companion object {
        fun from(name: String): Either<EmptySeatingSeatError, SeatingSeat> =
                if (name.isNotBlank()) {
                    SeatingSeat(name).right()
                } else {
                    EmptySeatingSeatError.left()
                }
    }
}

object EmptySeatingSeatError : BusinessError