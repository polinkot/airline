package com.example.airline.leasing.domain.seatmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class Row internal constructor(val value: Int) : ValueObject {

    companion object {
        fun from(row: Int): Either<NonPositiveSeatRowError, Row> =
                if (row > 0) {
                    Row(row).right()
                } else {
                    NonPositiveSeatRowError.left()
                }
    }
}

object NonPositiveSeatRowError : BusinessError