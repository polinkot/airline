package com.example.airline.maintenance.domain

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import kotlin.random.Random

fun flightId() = FlightId(Random.nextLong())

fun version() = Version.new()

fun airport(): Airport {
    val result = Airport.from("Airport ${Random.nextInt()}", "code_${Random.nextInt()}")
    check(result is Either.Right<Airport>)
    return result.b
}

fun duration(value: Int = Random.nextInt(20, 5000)): FlightDuration {
    val result = FlightDuration.from(value)
    check(result is Either.Right<FlightDuration>)
    return result.b
}

fun flight(
        arrivalAirport: Airport? = null,
        duration: FlightDuration = FlightDuration(0),
        state: FlightState = FlightState.IDLE
): Flight {
    return FlightRestorer.restore(
            id = flightId(),
            departureAirport = airport(),
            arrivalAirport = arrivalAirport,
            duration = duration,
            state = state,
            version = version()
    )
}