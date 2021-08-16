package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.usecase.TestTicketExtractor
import com.example.airline.flight.usecase.ticket
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import org.junit.jupiter.api.Test

internal class GetTicketsUseCaseTest {

    @Test
    fun `storage is empty`() {
        val extractor = TestTicketExtractor()
        val useCase = GetTicketsUseCase(extractor)
        val result = useCase.execute()
        result.shouldBeEmpty()
    }

    @Test
    fun `storage is not empty`() {
        val ticket = ticket()
        val extractor = TestTicketExtractor().apply {
            this[ticket.id] = ticket
        }

        val useCase = GetTicketsUseCase(extractor)
        val result = useCase.execute()
        result shouldContainExactly listOf(
                TicketInfo(
                        id = ticket.id,
                        price = ticket.price
                )
        )
    }
}