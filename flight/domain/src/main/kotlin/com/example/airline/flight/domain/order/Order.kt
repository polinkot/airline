package com.example.airline.flight.domain.order

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.DomainEvent
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Email
import com.example.airline.common.types.error.BusinessError
import com.example.airline.flight.domain.order.OrderCreationError.NoTicketsError
import com.example.airline.flight.domain.order.OrderCreationError.TicketsNotAvailableError
import com.example.airline.flight.domain.ticket.Price
import java.time.OffsetDateTime

class Order internal constructor(
        id: OrderId,
        val created: OffsetDateTime,
        val email: Email,
        val orderItems: Set<OrderItem>,
        version: Version
) : AggregateRoot<OrderId>(id, version) {
    var price: Price = Price.zero()

    var state: OrderState = OrderState.WAITING_FOR_PAYMENT
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
                    version = Version.new()
            ).apply {
                price = priceProvider.getPrice(ticketIds)
                addEvent(OrderCreatedDomainEvent(id))
            }.right()
        }
    }

    fun pay() = changeState(OrderState.PAID, OrderPaidDomainEvent(id))

    fun cancel() = changeState(OrderState.CANCELLED, OrderCancelledDomainEvent(id))

    private fun changeState(newState: OrderState, event: DomainEvent): Either<InvalidState, Unit> {
        return when {
            state == newState -> Unit.right()
            state.canChangeTo(newState) -> {
                state = newState
                addEvent(event)
                Unit.right()
            }
            else -> InvalidState.left()
        }
    }

    fun isActive(): Boolean = state.active
}

enum class OrderState(
        val active: Boolean,
        private val nextStates: Set<OrderState> = emptySet()
) {
    CANCELLED(active = false),
    PAID(active = true, nextStates = setOf(CANCELLED)),
    WAITING_FOR_PAYMENT(active = true, nextStates = setOf(PAID, CANCELLED));

    fun canChangeTo(state: OrderState) = nextStates.contains(state)
}

sealed class OrderCreationError : BusinessError {
    object NoTicketsError : OrderCreationError()
    object TicketsNotAvailableError : OrderCreationError()
}

object InvalidState : BusinessError