package com.example.airline.flight.domain.ticket

import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.flightId
import com.example.airline.flight.domain.price
import com.example.airline.flight.domain.ticket.TicketCreationError.FlightIsNotAnnounced
import com.example.airline.flight.domain.ticket.TicketCreationError.NotEnoughTimeToDeparture
import com.example.airline.flight.domain.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class TicketTest {
    val id = ticketId()

    private val idGenerator = object : TicketIdGenerator {
        override fun generate() = id
    }

    @Test
    fun `create ticket - success`() {
        val flightId = flightId()
        val price = price()
        val flightIsAnnounced = TestFlightIsAnnounced(true)
        val enoughTimeToDeparture = TestEnoughTimeToDeparture(true)

        val result = Ticket.create(idGenerator = idGenerator,
                flightId = flightId,
                price = price,
                flightIsAnnounced = flightIsAnnounced,
                enoughTimeToDeparture = enoughTimeToDeparture
        )

        result shouldBeRight {
            it.id shouldBe id
            it.flightId shouldBe flightId
            it.price shouldBe price

            it.popEvents() shouldContainExactly listOf(TicketCreatedDomainEvent(id))
        }
    }

    @Test
    fun `create ticket - flight is not announced`() {
        val result = Ticket.create(idGenerator = idGenerator,
                flightId = flightId(),
                price = price(),
                flightIsAnnounced = TestFlightIsAnnounced(false),
                enoughTimeToDeparture = TestEnoughTimeToDeparture(true)
        )

        result shouldBeLeft FlightIsNotAnnounced
    }

    @Test
    fun `create ticket - not enough time to departure`() {
        val result = Ticket.create(idGenerator = idGenerator,
                flightId = flightId(),
                price = price(),
                flightIsAnnounced = TestFlightIsAnnounced(true),
                enoughTimeToDeparture = TestEnoughTimeToDeparture(false)
        )

        result shouldBeLeft NotEnoughTimeToDeparture
    }

    class TestFlightIsAnnounced(val isAnnounced: Boolean) : FlightIsAnnounced {
        override fun check(flightId: FlightId): Boolean {
            return isAnnounced
        }
    }

    class TestEnoughTimeToDeparture(val isEnough: Boolean) : EnoughTimeToDeparture {
        override fun check(flightId: FlightId, hours: Long): Boolean {
            return isEnough
        }
    }
}