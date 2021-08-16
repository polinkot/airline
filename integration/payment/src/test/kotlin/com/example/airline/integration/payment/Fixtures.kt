package com.example.airline.integration.payment

import arrow.core.Either
import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.domain.ticket.Price
import java.math.BigDecimal
import kotlin.random.Random

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}

fun orderId() = OrderId(Random.nextLong())