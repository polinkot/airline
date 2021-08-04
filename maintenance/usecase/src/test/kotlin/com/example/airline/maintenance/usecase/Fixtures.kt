package com.example.airline.maintenance.usecase

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.maintenance.domain.*
import java.time.Duration
import java.util.*
import kotlin.random.Random

fun version() = Version.new()

fun airport(): Airport {
    val result = Airport.from("Airport ${Random.nextInt()}")
    check(result is Either.Right<Airport>)
    return result.b
}

fun duration(value: Long = Random.nextLong(1, 30)): Duration {
    return Duration.ofHours(value)
}

fun flightId(value: Long = Random.nextLong(20, 5000)): FlightId {
    val result = FlightId.from(value)
    check(result is Either.Right<FlightId>)
    return result.b
}

fun flightReadyToArrive() = flight(state = FlightState.REGISTERED, Duration.ZERO, null)
fun flightNotReadyToArrive() = flight(state = FlightState.ARRIVED, Duration.ofHours(3), airport())

fun flight(
        state: FlightState = FlightState.REGISTERED,
        duration: Duration = Duration.ZERO,
        arrivalAirport: Airport? = null
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

class TestFlightPersister : HashMap<FlightId, Flight>(), FlightPersister {
    override fun save(flight: Flight) {
        this[flight.id] = flight
    }
}

class TestFlightExtractor : FlightExtractor, LinkedHashMap<FlightId, Flight>() {
    override fun getById(id: FlightId) = this[id]

    override fun getAll() = values.toList()
}