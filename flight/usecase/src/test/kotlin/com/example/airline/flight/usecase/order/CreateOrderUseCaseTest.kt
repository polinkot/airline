package com.example.airline.flight.usecase.order

import com.example.airline.flight.domain.order.OrderId
import com.example.airline.flight.domain.order.OrderIdGenerator
import com.example.airline.flight.domain.order.TicketPriceProvider
import com.example.airline.flight.domain.order.TicketsAvailable
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId
import com.example.airline.flight.usecase.*
import com.example.airline.flight.usecase.order.CreateOrderUseCaseError.NoTicketsUseCaseError
import com.example.airline.flight.usecase.order.CreateOrderUseCaseError.TicketsNotAvailableUseCaseError
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.net.URL

internal class CreateOrderUseCaseTest {

    @Test
    fun `successfully added`() {
        val email = email()
        val orderItems = orderItems()

        val persister = TestOrderPersister()

        val result = CreateOrderUseCase(
                persister = persister,
                idGenerator = TestOrderIdGenerator,
                priceProvider = TestPriceProvider,
                ticketsAvailable = TicketsAvailableTrue,
                paymentUrlProvider = TestPaymentUrlProvider
        ).execute(
                CreateOrderRequest(
                        email = email,
                        orderItems = orderItems
                )
        )

        val id = TestOrderIdGenerator.id

        val order = persister[id]
        order.shouldNotBeNull()

        result shouldBeRight {
            it.orderId shouldBe id
            it.paymentURL shouldBe TestPaymentUrlProvider.paymentUrl
            it.price shouldBe order.price
        }

        order.id shouldBe id
        order.email shouldBe email
        order.orderItems shouldContainExactly orderItems
    }

    @Test
    fun `empty order`() {
        val persister = TestOrderPersister()

        val result = CreateOrderUseCase(
                persister = persister,
                idGenerator = TestOrderIdGenerator,
                priceProvider = TestPriceProvider,
                ticketsAvailable = TicketsAvailableTrue,
                paymentUrlProvider = TestPaymentUrlProvider
        ).execute(
                CreateOrderRequest(
                        email = email(),
                        orderItems = emptySet()
                )
        )

        result shouldBeLeft NoTicketsUseCaseError
        persister.shouldBeEmpty()
    }

    @Test
    fun `tickets are not available`() {
        val persister = TestOrderPersister()

        val result = CreateOrderUseCase(
                persister = persister,
                idGenerator = TestOrderIdGenerator,
                priceProvider = TestPriceProvider,
                ticketsAvailable = TicketsAvailableFalse,
                paymentUrlProvider = TestPaymentUrlProvider
        ).execute(
                CreateOrderRequest(
                        email = email(),
                        orderItems = orderItems()
                )
        )

        result shouldBeLeft TicketsNotAvailableUseCaseError
        persister.shouldBeEmpty()
    }

    object TestOrderIdGenerator : OrderIdGenerator {
        val id = orderId()
        override fun generate() = id
    }

    object TicketsAvailableTrue : TicketsAvailable {
        override fun check(ticketIds: Set<TicketId>) = true
    }

    object TicketsAvailableFalse : TicketsAvailable {
        override fun check(ticketIds: Set<TicketId>) = false
    }

    object TestPriceProvider : TicketPriceProvider {
        val price = price()
        override fun getPrice(ticketId: TicketId) = price
    }

    object TestPaymentUrlProvider : PaymentUrlProvider {
        val paymentUrl = URL("http://localhost")
        override fun provideUrl(orderId: OrderId, price: Price) = paymentUrl
    }
}