package com.example.airline.flight.usecase.order

import com.example.airline.flight.domain.order.OrderPaidDomainEvent
import com.example.airline.flight.usecase.*
import com.example.airline.flight.usecase.order.PayOrderUseCaseError.InvalidOrderState
import com.example.airline.flight.usecase.order.PayOrderUseCaseError.OrderNotFound
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.nulls.shouldNotBeNull
import org.junit.jupiter.api.Test

internal class PayOrderUseCaseTest {

    @Test
    fun `successfully payed`() {
        val orderReadyForPay = orderReadyForPay()
        val extractor = TestOrderExtractor().apply {
            this[orderReadyForPay.id] = orderReadyForPay
        }
        val persister = TestOrderPersister()

        val useCase = PayOrderUseCase(extractor, persister)
        val result = useCase.execute(orderId = orderReadyForPay.id)

        result.shouldBeRight()

        val order = persister[orderReadyForPay.id]
        order.shouldNotBeNull()
        order.popEvents() shouldContainExactly listOf(OrderPaidDomainEvent(orderReadyForPay.id))
    }

    @Test
    fun `invalid state`() {
        val order = orderNotReadyForPay()
        val extractor = TestOrderExtractor().apply {
            this[order.id] = order
        }
        val persister = TestOrderPersister()

        val useCase = PayOrderUseCase(extractor, persister)
        val result = useCase.execute(orderId = order.id)
        result shouldBeLeft InvalidOrderState
    }

    @Test
    fun `order not found`() {
        val extractor = TestOrderExtractor()
        val persister = TestOrderPersister()

        val useCase = PayOrderUseCase(extractor, persister)
        val result = useCase.execute(orderId = orderId())
        result shouldBeLeft OrderNotFound
    }
}