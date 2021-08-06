package com.example.airline.common.types

import arrow.core.Either
import com.example.airline.common.types.common.Count

fun count(value: Int): Count {
    val result = Count.from(value)
    check(result is Either.Right<Count>)
    return result.b
}