package com.example.airline.flight.domain

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.Count
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.Price
import com.example.airline.flight.domain.ticket.TicketId
import java.math.BigDecimal
import kotlin.random.Random

fun flightId() = FlightId(Random.nextLong())

fun aircraftId() = AircraftId(Random.nextLong())

fun ticketId() = TicketId(Random.nextLong())

fun version() = Version.new()

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
    val result = Airport.from("Airport ${Random.nextInt()}", "code_${Random.nextInt()}")
    check(result is Either.Right<Airport>)
    return result.b
}

fun price(value: BigDecimal = BigDecimal(Random.nextInt(1, 500000))): Price {
    val result = Price.from(value)
    check(result is Either.Right<Price>)
    return result.b
}