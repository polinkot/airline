package com.example.airline.flight.usecase

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.flight.domain.aircraft.Aircraft
import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.aircraft.AircraftRestorer
import com.example.airline.flight.domain.flight.Flight
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.flight.FlightRestorer
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.Ticket
import com.example.airline.flight.domain.ticket.TicketId
import com.example.airline.flight.usecase.aircraft.AircraftExtractor
import com.example.airline.flight.usecase.aircraft.AircraftPersister
import com.example.airline.flight.usecase.flight.AirportIntegrationService
import com.example.airline.flight.usecase.flight.FlightExtractor
import com.example.airline.flight.usecase.flight.FlightPersister
import com.example.airline.flight.usecase.ticket.TicketPersister
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*
import kotlin.random.Random

fun version() = Version.new()

fun aircraftId() = AircraftId(Random.nextLong(1, 5000))

fun aircraft(
): Aircraft {
    return AircraftRestorer.restore(
            id = aircraftId(),
            manufacturer = manufacturer(),
            seatsCount = count(),
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

fun flight(): Flight {
    return FlightRestorer.restore(
            id = flightId(),
            departureAirport = airport(),
            arrivalAirport = airport(),
            flightDate = flightDate(),
            aircraftId = aircraftId(),
            version = version()
    )
}

fun ticketId() = TicketId(Random.nextLong(1, 5000))

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}

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