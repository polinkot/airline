package com.example.airline.flight.domain.order

import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Email
import com.example.airline.flight.domain.ticket.Price
import java.time.OffsetDateTime

@Suppress("LongParameterList")
object OrderRestorer {
    fun restore(
            id: OrderId,
            created: OffsetDateTime,
            email: Email,
            orderItems: Set<OrderItem>,
            price: Price,
            state: OrderState,
            version: Version
    ): Order {
        return Order(
                id = id,
                created = created,
                email = email,
                orderItems = orderItems,
                version = version
        ).apply {
            this.price = price
            this.state = state
        }
    }
}