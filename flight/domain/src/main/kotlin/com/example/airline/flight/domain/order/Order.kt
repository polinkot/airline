package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Email
import com.example.airline.common.types.error.BusinessError
import com.example.airline.flight.domain.order.OrderCreationError.NoTicketsError
import com.example.airline.flight.domain.order.OrderCreationError.TicketsNotAvailableError
import com.example.airline.flight.domain.order.OrderState.CREATED
import com.example.airline.flight.domain.order.OrderState.PAID
import com.example.airline.flight.domain.ticket.Price
import java.time.OffsetDateTime

class Order internal constructor(
        id: OrderId,
        val created: OffsetDateTime,
        val email: Email,
        val orderItems: Set<OrderItem>,
        val price: Price,
        version: Version
) : AggregateRoot<OrderId>(id, version) {

    var state: OrderState = CREATED
        internal set

    companion object {
        fun create(
                idGenerator: OrderIdGenerator,
                ticketsAvailable: TicketsAvailable,
                email: Email,
                orderItems: Set<OrderItem>,
                priceProvider: OrderPriceProvider
        ): Either<OrderCreationError, Order> {
            if (orderItems.isEmpty()) {
                return NoTicketsError.left()
            }

            val ticketIds = orderItems.map(OrderItem::ticketId).toSet()
            if (!ticketsAvailable.check(ticketIds)) {
                return TicketsNotAvailableError.left()
            }

            return Order(
                    id = idGenerator.generate(),
                    created = OffsetDateTime.now(),
                    email = email,
                    orderItems = orderItems,
                    price = priceProvider.getPrice(ticketIds),
                    version = Version.new()
            ).apply {
                addEvent(OrderCreatedDomainEvent(id))
            }.right()
        }
    }

    fun pay() {
        if (!state.canChangeTo(PAID)) {
            return
        }

        state = PAID
        addEvent(OrderPaidDomainEvent(id))
    }
}

enum class OrderState(
        private val nextStates: Set<OrderState> = emptySet()
) {
    PAID(nextStates = emptySet()),
    CREATED(nextStates = setOf(PAID));

    fun canChangeTo(state: OrderState) = nextStates.contains(state)
}

sealed class OrderCreationError : BusinessError {
    object NoTicketsError : OrderCreationError()
    object TicketsNotAvailableError : OrderCreationError()
}