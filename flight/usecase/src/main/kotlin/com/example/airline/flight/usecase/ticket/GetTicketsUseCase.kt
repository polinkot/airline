package com.example.airline.flight.usecase.ticket

class GetTicketsUseCase(private val ticketExtractor: TicketExtractor) : GetTickets {
    override fun execute(): List<TicketInfo> {
        return ticketExtractor.getAll().map {
            TicketInfo(
                    id = it.id,
                    price = it.price
            )
        }.toList()
    }
}