package com.example.airline.flight.usecase.ticket

import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.FlightIsAnnounced
import com.example.airline.flight.domain.ticket.MoreThanHourTillDeparture
import com.example.airline.flight.domain.ticket.TicketIdGenerator
import com.example.airline.flight.usecase.TestTicketPersister
import com.example.airline.flight.usecase.flightId
import com.example.airline.flight.usecase.price
import com.example.airline.flight.usecase.ticket.CreateTicketUseCaseError.FlightIsNotAnnouncedUseCaseError
import com.example.airline.flight.usecase.ticket.CreateTicketUseCaseError.LessThanHourTillDepartureUseCaseError
import com.example.airline.flight.usecase.ticketId
import io.kotest.assertions.arrow.either.shouldBeLeft
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.matchers.maps.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CreateTicketUseCaseTest {

    @Test
    fun `successfully added`() {
        val flightId = flightId()
        val price = price()

        val persister = TestTicketPersister()

        val result = CreateTicketUseCase(
                persister = persister,
                idGenerator = TestTicketIdGenerator,
                flightIsAnnounced = FlightIsAnnouncedTrue,
                moreThanHourTillDeparture = MoreThanHourTillDepartureTrue
        ).execute(
                CreateTicketRequest(flightId = flightId, price = price)
        )

        val id = TestTicketIdGenerator.id

        result shouldBeRight {
            it shouldBe id
        }

        val ticket = persister[id]
        ticket.shouldNotBeNull()

        ticket.id shouldBe id
        ticket.flightId shouldBe flightId
        ticket.price shouldBe price
    }

    @Test
    fun `flight is not announced`() {
        val persister = TestTicketPersister()

        val result = CreateTicketUseCase(
                persister = persister,
                idGenerator = TestTicketIdGenerator,
                flightIsAnnounced = FlightIsAnnouncedFalse,
                moreThanHourTillDeparture = MoreThanHourTillDepartureTrue
        ).execute(
                CreateTicketRequest(flightId = flightId(), price = price())
        )

        result shouldBeLeft FlightIsNotAnnouncedUseCaseError
        persister.shouldBeEmpty()
    }

    @Test
    fun `less than hour till departure`() {
        val persister = TestTicketPersister()

        val result = CreateTicketUseCase(
                persister = persister,
                idGenerator = TestTicketIdGenerator,
                flightIsAnnounced = FlightIsAnnouncedTrue,
                moreThanHourTillDeparture = MoreThanHourTillDepartureFalse
        ).execute(
                CreateTicketRequest(flightId = flightId(), price = price())
        )

        result shouldBeLeft LessThanHourTillDepartureUseCaseError
        persister.shouldBeEmpty()
    }

    object TestTicketIdGenerator : TicketIdGenerator {
        val id = ticketId()
        override fun generate() = id
    }

    object FlightIsAnnouncedTrue : FlightIsAnnounced {
        override fun check(flightId: FlightId) = true
    }

    object FlightIsAnnouncedFalse : FlightIsAnnounced {
        override fun check(flightId: FlightId) = false
    }

    object MoreThanHourTillDepartureTrue : MoreThanHourTillDeparture {
        override fun check(flightId: FlightId) = true
    }

    object MoreThanHourTillDepartureFalse : MoreThanHourTillDeparture {
        override fun check(flightId: FlightId) = false
    }
}