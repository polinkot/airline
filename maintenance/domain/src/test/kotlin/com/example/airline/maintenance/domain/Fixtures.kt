package com.example.airline.maintenance.domain

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.maintenance.domain.FlightState.REGISTERED
import java.time.Duration
import kotlin.random.Random

fun flightId() = FlightId(Random.nextLong())

fun version() = Version.new()

fun airport(): Airport {
    val result = Airport.from("Airport ${Random.nextInt()}")
    check(result is Either.Right<Airport>)
    return result.b
}

fun duration(value: Long = Random.nextLong(1, 30)): Duration {
    return Duration.ofHours(value)
}

fun flight(
        arrivalAirport: Airport? = null,
        duration: Duration = Duration.ZERO,
        state: FlightState = REGISTERED
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