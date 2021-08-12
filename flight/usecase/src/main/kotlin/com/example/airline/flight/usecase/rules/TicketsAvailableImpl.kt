package com.example.airline.flight.usecase.rules

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import arrow.core.rightIfNotNull
import com.example.airline.common.types.common.Count
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.order.TicketsAvailable
import com.example.airline.flight.domain.ticket.TicketId
import com.example.airline.flight.usecase.aircraft.AircraftExtractor
import com.example.airline.flight.usecase.flight.FlightExtractor
import com.example.airline.flight.usecase.ticket.TicketExtractor

class TicketsAvailableImpl(private val ticketExtractor: TicketExtractor,
                           private val flightExtractor: FlightExtractor,
                           private val aircraftExtractor: AircraftExtractor) : TicketsAvailable {
    override fun check(ticketIds: Set<TicketId>): Boolean {
        val flightTickets = ticketExtractor.getByIds(ticketIds)
                .groupingBy { it.flightId }.eachCount()
        if (flightTickets.values.sum() < ticketIds.size) {
            return false
        }

        for ((flightId, requiredCount) in flightTickets) {
            println("$flightId = $requiredCount")
            val totalSeatsCount = getTotalSeatsCount(flightId).value
            val soldCount = ticketExtractor.getSoldOutByFlightId(flightId).size
            if (soldCount + requiredCount > totalSeatsCount) {
                return false
            }
        }

        return true
    }

    private fun getTotalSeatsCount(flightId: FlightId): Count {
        val result = flightExtractor.getById(flightId)
                .rightIfNotNull { "Flight not found for flightId = $flightId" }
                .flatMap { flight ->
                    aircraftExtractor.getById(flight.aircraftId)
                            .rightIfNotNull { "Aircraft not found for flightId = $flightId" }
                }.flatMap { aircraft ->
                    aircraft.seatsCount.right()
                }

        check(result is Either.Right<Count>)
        return result.b
    }
}