package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.*
import com.example.airline.flight.domain.order.OrderState.CREATED
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

class OrderRestorerTest {
    @Test
    fun `restore order - success`() {
        val id = orderId()
        val created = OffsetDateTime.now()
        val email = email()
        val orderItem = orderItem()
        val orderItems = setOf(orderItem)
        val price = price()
        val state = CREATED
        val version = version()

        val order = OrderRestorer.restore(
                id = id,
                created = created,
                email = email,
                orderItems = orderItems,
                price = price,
                state = state,
                version = version
        )

        order.id shouldBe id
        order.created shouldBe created
        order.email shouldBe email
        order.orderItems shouldBe orderItems
        order.price shouldBe price
        order.state shouldBe state
        order.version shouldBe version

        order.popEvents().shouldBeEmpty()
    }
}