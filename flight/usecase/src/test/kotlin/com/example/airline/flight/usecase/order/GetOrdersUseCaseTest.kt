package com.example.airline.flight.usecase.order

import com.example.airline.flight.usecase.TestOrderExtractor
import com.example.airline.flight.usecase.order
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class GetOrdersUseCaseTest {

    @Test
    fun `storage is empty`() {
        val extractor = TestOrderExtractor()
        val useCase = GetOrdersUseCase(extractor)
        val result = useCase.execute()
        result.shouldBeEmpty()
    }

    @Test
    fun `storage is not empty`() {
        val order = order()
        val extractor = TestOrderExtractor().apply {
            this[order.id] = order
        }

        val useCase = GetOrdersUseCase(extractor)
        val result = useCase.execute()
        result shouldContainExactly listOf(
                OrderInfo(
                        id = order.id,
                        created = order.created,
                        email = order.email
                )
        )
    }
}