package com.example.airline.flight.usecase.rules

import com.example.airline.common.types.common.Count
import com.example.airline.flight.domain.ticket.Ticket
import com.example.airline.flight.usecase.*
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.jupiter.api.Test

internal class TicketsAvailableImplTest {

    @Test
    fun `tickets available`() {
        val aircraft = aircraft(Count(4))

        val flight1 = flight(aircraftId = aircraft.id)
        val flight2 = flight(aircraftId = aircraft.id)

        val ticket1 = ticket(flight1.id)
        val ticket2 = ticket(flight1.id)
        val ticket3 = ticket(flight1.id)
        val ticket4 = ticket(flight2.id)
        val ticket5 = ticket(flight2.id)

        val ticketExtractor = TestTicketExtractor().apply {
            this[ticket1.id] = ticket1
            this[ticket2.id] = ticket2
            this[ticket3.id] = ticket3
            this[ticket4.id] = ticket4
            this[ticket5.id] = ticket5
        }

        val flightExtractor = TestFlightExtractor().apply {
            this[flight1.id] = flight1
            this[flight2.id] = flight2
        }

        val aircraftExtractor = TestAircraftExtractor().apply {
            this[aircraft.id] = aircraft
        }

        val rule = TicketsAvailableImpl(ticketExtractor, flightExtractor, aircraftExtractor)

        val ticketIds = ticketExtractor.values.map(Ticket::id).toSet()

        val isTicketsAvailable = rule.check(ticketIds)
        isTicketsAvailable.shouldBeTrue()
    }

    @Test
    fun `tickets not available - sold out`() {
        val aircraft = aircraft(Count(3))

        val flight = flight(aircraftId = aircraft.id)

        val ticket1 = ticket(flight.id)
        val ticket2 = ticket(flight.id)
        val ticket3 = ticket(flight.id)

        val ticketExtractor = TestTicketExtractor().apply {
            this[ticket1.id] = ticket1
            this[ticket2.id] = ticket2
            this[ticket3.id] = ticket3
        }

        val flightExtractor = TestFlightExtractor().apply {
            this[flight.id] = flight
        }

        val aircraftExtractor = TestAircraftExtractor().apply {
            this[aircraft.id] = aircraft
        }

        val rule = TicketsAvailableImpl(ticketExtractor, flightExtractor, aircraftExtractor)

        val ticketIds = ticketExtractor.values.map(Ticket::id).toSet()

        val isTicketsAvailable = rule.check(ticketIds)
        isTicketsAvailable.shouldBeFalse()
    }
}