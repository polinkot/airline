package com.example.airline.flight.usecase.order

import com.example.airline.common.types.common.Email
import com.example.airline.flight.domain.order.OrderId
import java.time.OffsetDateTime

interface GetOrders {
    fun execute(): List<OrderInfo>
}

data class OrderInfo(
        val id: OrderId,
        val created: OffsetDateTime,
        val email: Email
)