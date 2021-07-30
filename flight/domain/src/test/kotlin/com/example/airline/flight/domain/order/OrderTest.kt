package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.*
import com.example.airline.flight.domain.order.OrderCreationError.NoTicketsError
import com.example.airline.flight.domain.order.OrderCreationError.TicketsNotAvailableError
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class OrderTest {
    val id = orderId()

    private val idGenerator = object : OrderIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create order - success`() {
        val price = price()
        val email = email()
        val orderItem = orderItem()
        val orderItems = setOf(orderItem)

        val result = Order.create(idGenerator = idGenerator,
                ticketsAvailable = TestTicketsAvailable(true),
                email = email,
                orderItems = orderItems,
                priceProvider = TestOrderPriceProvider(price)
        )

        result shouldBeRight {
            it.id shouldBe id
            it.email shouldBe email
            it.orderItems shouldContainExactly setOf(orderItem)
            it.price shouldBe price
            it.state shouldBe OrderState.WAITING_FOR_PAYMENT

            it.popEvents() shouldContainExactly listOf(OrderCreatedDomainEvent(id))
        }
    }

    @Test
    fun `create order - no tickets`() {
        val result = Order.create(idGenerator = idGenerator,
                ticketsAvailable = TestTicketsAvailable(false),
                email = email(),
                orderItems = emptySet(),
                priceProvider = TestOrderPriceProvider(price())
        )

        result shouldBeLeft NoTicketsError
    }

    @Test
    fun `create order - tickets are not available`() {
        val result = Order.create(idGenerator = idGenerator,
                ticketsAvailable = TestTicketsAvailable(false),
                email = email(),
                orderItems = setOf(orderItem()),
                priceProvider = TestOrderPriceProvider(price())
        )

        result shouldBeLeft TicketsNotAvailableError
    }

    @ParameterizedTest
    @EnumSource(names = ["WAITING_FOR_PAYMENT", "PAID"])
    fun `active - true`(state: OrderState) {
        val order = order(state = state)
        order.isActive() shouldBe true
    }

    @ParameterizedTest
    @EnumSource(names = ["CANCELLED"])
    fun `active - false`(state: OrderState) {
        val order = order(state = state)
        order.isActive() shouldBe false
    }

    @Test
    fun `pay - success`() {
        val order = order(state = OrderState.WAITING_FOR_PAYMENT)
        order.pay()
        order.state shouldBe OrderState.PAID
        order.popEvents() shouldContainExactly listOf(OrderPaidDomainEvent(order.id))
    }

    @Test
    fun `pay - already`() {
        val order = order(state = OrderState.PAID)
        order.pay()
        order.state shouldBe OrderState.PAID
        order.popEvents().shouldBeEmpty()
    }

    @ParameterizedTest
    @EnumSource(names = ["CANCELLED"])
    fun `pay - invalid state`(state: OrderState) {
        val order = order(state = state)
        order.pay()
        order.state shouldBe state
        order.popEvents().shouldBeEmpty()
    }

    @Test
    fun `cancel - success`() {
        val order = order(state = OrderState.WAITING_FOR_PAYMENT)
        order.cancel()
        order.state shouldBe OrderState.CANCELLED
        order.popEvents() shouldContainExactly listOf(OrderCancelledDomainEvent(order.id))
    }

    @Test
    fun `cancel - already`() {
        val order = order(state = OrderState.CANCELLED)
        order.cancel()
        order.state shouldBe OrderState.CANCELLED
        order.popEvents().shouldBeEmpty()
    }

    @ParameterizedTest
    @EnumSource(names = ["PAID"])
    fun `cancel - invalid state`(state: OrderState) {
        val order = order(state = state)
        order.cancel()
        order.state shouldBe state
        order.popEvents().shouldBeEmpty()
    }

    class TestTicketsAvailable(private val isAvailable: Boolean) : TicketsAvailable {
        override fun check(ticketIds: Set<TicketId>): Boolean {
            return isAvailable
        }
    }

    class TestOrderPriceProvider(private val price: Price) : OrderPriceProvider {
        override fun getPrice(tickets: Set<TicketId>): Price {
            return price
        }
    }
}