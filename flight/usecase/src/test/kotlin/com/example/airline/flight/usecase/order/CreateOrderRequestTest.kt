package com.example.airline.flight.usecase.order

import com.example.airline.flight.usecase.email
import com.example.airline.flight.usecase.orderItems
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import org.junit.jupiter.api.Test

class CreateOrderRequestTest {

    @Test
    fun `successfully created`() {
        val email = email()
        val orderItems = orderItems()

        val items = orderItems.map { OrderItemData(it.ticketId.value, it.fullName.value, it.passport.value) }.toSet()

        val result = CreateOrderRequest.from(
                email = email.value,
                items = items
        )

        result shouldBeRight CreateOrderRequest(email, orderItems)
    }

    @Test
    fun `invalid email`() {
        val orderItems = orderItems()
        val items = orderItems.map { OrderItemData(it.ticketId.value, it.fullName.value, it.passport.value) }.toSet()

        val result = CreateOrderRequest.from(
                email = "",
                items = items
        )

        result shouldBeLeft InvalidOrderParameters("Invalid email")
    }

    @Test
    fun `empty order`() {
        val result = CreateOrderRequest.from(
                email = email().value,
                items = emptySet()
        )

        result shouldBeLeft InvalidOrderParameters("Empty order")
    }
}