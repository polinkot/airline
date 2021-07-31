package com.example.airline.flight.domain.order

import com.example.airline.flight.domain.fullName
import com.example.airline.flight.domain.passport
import com.example.airline.flight.domain.ticketId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class OrderItemTest {

    @Test
    fun `create orderItem - success`() {
        val ticketId = ticketId()
        val fullName = fullName()
        val passport = passport()

        val result = OrderItem.from(
                ticketId = ticketId,
                fullName = fullName,
                passport = passport)

        result.ticketId shouldBe ticketId
        result.fullName shouldBe fullName
        result.passport shouldBe passport
    }
}