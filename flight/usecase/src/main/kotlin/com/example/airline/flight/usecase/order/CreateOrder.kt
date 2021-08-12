package com.example.airline.flight.usecase.order

import arrow.core.Either
import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.domain.ticket.Price
import java.net.URL

interface CreateOrder {
    fun execute(request: CreateOrderRequest): Either<CreateOrderUseCaseError, PaymentInfo>
}

data class PaymentInfo(
        val orderId: OrderId,
        val price: Price,
        val paymentURL: URL
)

sealed class CreateOrderUseCaseError(open val message: String) {
    object NoTicketsUseCaseError : CreateOrderUseCaseError("No tickets")
    object TicketsNotAvailableUseCaseError : CreateOrderUseCaseError("Tickets are not available")
}