package com.example.airline.leasing.domain.seatmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class SeatMapName internal constructor(val value: String) : ValueObject {

    companion object {
        fun from(name: String): Either<EmptySeatMapNameError, SeatMapName> =
                if (name.isNotBlank()) {
                    SeatMapName(name).right()
                } else {
                    EmptySeatMapNameError.left()
                }
    }
}

object EmptySeatMapNameError : BusinessError