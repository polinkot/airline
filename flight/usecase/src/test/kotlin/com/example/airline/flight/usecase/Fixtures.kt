package com.example.airline.flight.usecase

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Email
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.flight.domain.aircraft.Aircraft
import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.aircraft.AircraftRestorer
import com.example.airline.flight.domain.flight.Flight
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.flight.FlightRestorer
import com.example.airline.flight.domain.order.*
import com.example.airline.flight.domain.order.OrderState.CREATED
import com.example.airline.flight.domain.order.OrderState.PAID
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.Ticket
import com.example.airline.flight.domain.ticket.TicketId
import com.example.airline.flight.domain.ticket.TicketRestorer
import com.example.airline.flight.usecase.aircraft.AircraftExtractor
import com.example.airline.flight.usecase.aircraft.AircraftPersister
import com.example.airline.flight.usecase.flight.AirportIntegrationService
import com.example.airline.flight.usecase.flight.FlightExtractor
import com.example.airline.flight.usecase.flight.FlightPersister
import com.example.airline.flight.usecase.order.OrderExtractor
import com.example.airline.flight.usecase.order.OrderPersister
import com.example.airline.flight.usecase.ticket.TicketExtractor
import com.example.airline.flight.usecase.ticket.TicketPersister
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import kotlin.random.Random

fun version() = Version.new()

fun aircraftId() = AircraftId(Random.nextLong(1, 5000))

fun aircraft(seatsCount: Count = count()): Aircraft {
    return AircraftRestorer.restore(
            id = aircraftId(),
            manufacturer = manufacturer(),
            seatsCount = seatsCount,
            version = version()
    )
}

fun manufacturer(): Manufacturer {
    val result = Manufacturer.from("Manufacturer ${Random.nextInt()}")
    check(result is Either.Right<Manufacturer>)
    return result.b
}

fun count(value: Int = Random.nextInt(20, 5000)): Count {
    val result = Count.from(value)
    check(result is Either.Right<Count>)
    return result.b
}

fun airport(): Airport {
    val result = Airport.from("Airport ${Random.nextInt()}")
    check(result is Either.Right<Airport>)
    return result.b
}

fun flightDate(): OffsetDateTime {
    return OffsetDateTime.now()
}

fun flightId() = FlightId(Random.nextLong(1, 5000))

fun flight(flightDate: OffsetDateTime = flightDate(), aircraftId: AircraftId = aircraftId()): Flight {
    return FlightRestorer.restore(
            id = flightId(),
            departureAirport = airport(),
            arrivalAirport = airport(),
            flightDate = flightDate,
            aircraftId = aircraftId,
            version = version()
    )
}

fun ticketId() = TicketId(Random.nextLong(1, 5000))

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}

fun ticket(flightId: FlightId = flightId()): Ticket {
    return TicketRestorer.restore(
            id = ticketId(),
            flightId = flightId,
            price = price(),
            version = version()
    )
}

fun email(): Email {
    val result = Email.from("Email${Random.nextInt()}@mail.ru")
    check(result is Either.Right<Email>)
    return result.b
}

fun fullName(): FullName {
    val result = FullName.from("FullName ${Random.nextInt()}")
    check(result is Either.Right<FullName>)
    return result.b
}

fun passport(): Passport {
    val result = Passport.from("Passport${Random.nextInt()}")
    check(result is Either.Right<Passport>)
    return result.b
}

fun orderItems(): Set<OrderItem> {
    return setOf(
            OrderItem.from(ticketId(), fullName(), passport()),
            OrderItem.from(ticketId(), fullName(), passport()),
            OrderItem.from(ticketId(), fullName(), passport())
    )
}

fun orderId() = OrderId(Random.nextLong(1, 1000))

fun order(
        state: OrderState = CREATED,
        orderItems: Set<OrderItem> = orderItems(),
): Order {
    return OrderRestorer.restore(
            id = orderId(),
            created = OffsetDateTime.now(),
            email = email(),
            orderItems = orderItems,
            price = price(),
            state = state,
            version = version()
    )
}

fun orderReadyForPay() = order(state = CREATED)

fun orderNotReadyForPay() = order(state = PAID)

class TestAircraftPersister : HashMap<AircraftId, Aircraft>(), AircraftPersister {
    override fun save(aircraft: Aircraft) {
        this[aircraft.id] = aircraft
    }
}

class TestAircraftExtractor : AircraftExtractor, LinkedHashMap<AircraftId, Aircraft>() {
    override fun getById(id: AircraftId) = this[id]

    override fun getAll() = values.toList()
}

class TestFlightPersister : HashMap<FlightId, Flight>(), FlightPersister {
    override fun save(flight: Flight) {
        this[flight.id] = flight
    }
}

class TestFlightExtractor : FlightExtractor, LinkedHashMap<FlightId, Flight>() {
    override fun getById(id: FlightId) = this[id]

    override fun getAll() = values.toList()

    override fun getByAircraftId(aircraftId: AircraftId): List<Flight> {
        return this.values.filter { it.aircraftId == aircraftId }
    }
}

class TestAirportIntegrationService(
        val stub: Boolean
) : AirportIntegrationService {
    override fun checkTime(datetime: OffsetDateTime): Boolean {
        return stub
    }
}

class TestTicketPersister : HashMap<TicketId, Ticket>(), TicketPersister {
    override fun save(ticket: Ticket) {
        this[ticket.id] = ticket
    }
}

class TestTicketExtractor(val stubSoldOutCount: Int = 1) : TicketExtractor, LinkedHashMap<TicketId, Ticket>() {
    override fun getById(id: TicketId) = this[id]

    override fun getByIds(ids: Set<TicketId>): Set<Ticket> {
        return this.values.filter { ids.contains(it.id) }.toSet()
    }

    override fun getAll() = values.toList()

    override fun getSoldOutByFlightId(flightId: FlightId): List<Ticket> {
        return this.values.filter { it.flightId == flightId }.subList(0, stubSoldOutCount)
    }
}

class TestOrderPersister : HashMap<OrderId, Order>(), OrderPersister {
    override fun save(order: Order) {
        this[order.id] = order
    }
}

class TestOrderExtractor : OrderExtractor, LinkedHashMap<OrderId, Order>() {
    override fun getById(id: OrderId) = this[id]

    override fun getAll() = values.toList()
}