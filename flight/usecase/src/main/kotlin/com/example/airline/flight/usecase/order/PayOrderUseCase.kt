package com.example.airline.flight.usecase.order

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.rightIfNotNull
import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.usecase.order.PayOrderUseCaseError.InvalidOrderState
import com.example.airline.flight.usecase.order.PayOrderUseCaseError.OrderNotFound

class PayOrderUseCase(
        private val extractor: OrderExtractor,
        private val persister: OrderPersister
) : PayOrder {
    override fun execute(orderId: OrderId): Either<PayOrderUseCaseError, Unit> {
        return extractor.getById(orderId)
                .rightIfNotNull { OrderNotFound }
                .flatMap { order ->
                    order.pay().map {
                        persister.save(order)
                    }.mapLeft { InvalidOrderState }
                }
    }
}