package com.example.airline.flight.usecase.order

import arrow.core.Either
import com.example.airline.flight.domain.order.OrderId

interface PayOrder {
    fun execute(orderId: OrderId): Either<PayOrderUseCaseError, Unit>
}

sealed class PayOrderUseCaseError(val message: String) {
    object OrderNotFound : PayOrderUseCaseError("Order not found")
    object InvalidOrderState : PayOrderUseCaseError("Invalid order state")
}