package com.example.airline.flight.usecase.order

import arrow.core.Either
import com.example.airline.flight.domain.order.*
import com.example.airline.flight.domain.order.OrderCreationError.NoTicketsError
import com.example.airline.flight.domain.order.OrderCreationError.TicketsNotAvailableError
import com.example.airline.flight.usecase.order.CreateOrderUseCaseError.NoTicketsUseCaseError
import com.example.airline.flight.usecase.order.CreateOrderUseCaseError.TicketsNotAvailableUseCaseError

class CreateOrderUseCase(
        private val persister: OrderPersister,
        private val idGenerator: OrderIdGenerator,
        private val priceProvider: TicketPriceProvider,
        private val ticketsAvailable: TicketsAvailable,
        private val paymentUrlProvider: PaymentUrlProvider,
) : CreateOrder {
    override fun execute(request: CreateOrderRequest): Either<CreateOrderUseCaseError, PaymentInfo> =
            Order.create(
                    idGenerator = idGenerator,
                    ticketsAvailable = ticketsAvailable,
                    email = request.email,
                    orderItems = request.orderItems,
                    priceProvider = priceProvider
            ).mapLeft { e -> e.toError() }
                    .map { order ->
                        persister.save(order)
                        PaymentInfo(
                                orderId = order.id,
                                price = order.price,
                                paymentURL = paymentUrlProvider.provideUrl(order.id, order.price)
                        )
                    }
}

fun OrderCreationError.toError(): CreateOrderUseCaseError {
    return when (this) {
        NoTicketsError -> NoTicketsUseCaseError
        TicketsNotAvailableError -> TicketsNotAvailableUseCaseError
    }
}