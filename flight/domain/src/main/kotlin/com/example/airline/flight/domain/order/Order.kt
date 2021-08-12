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
        version: Version
) : AggregateRoot<OrderId>(id, version) {
    var price: Price = Price.zero()

    var state: OrderState = CREATED
        internal set

    companion object {
        fun create(
                idGenerator: OrderIdGenerator,
                ticketsAvailable: TicketsAvailable,
                email: Email,
                orderItems: Set<OrderItem>,
                priceProvider: TicketPriceProvider
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
                price = ticketIds.map { priceProvider.getPrice(it) }.reduce { sum, price -> sum.add(price) }
                addEvent(OrderCreatedDomainEvent(id))
            }.right()
        }
    }

    fun pay(): Either<InvalidState, Unit> {
        if (!state.canChangeTo(PAID)) {
            return InvalidState.left()
        }

        state = PAID
        addEvent(OrderPaidDomainEvent(id))
        return Unit.right()
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

object InvalidState : BusinessError