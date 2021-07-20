package com.example.airline.leasing.domain.seatingmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class SeatingRow internal constructor(val value: Int) : ValueObject {

    companion object {
        fun from(row: Int): Either<NegativeSeatingRowError, SeatingRow> =
                if (row > 0) {
                    SeatingRow(row).right()
                } else {
                    NegativeSeatingRowError.left()
                }
    }
}

object NegativeSeatingRowError : BusinessError