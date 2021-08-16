package com.example.airline.app

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.flight.domain.flight.Flight
import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.flight.FlightRestorer
import com.example.airline.flight.usecase.flight.FlightExtractor
import com.example.airline.leasing.domain.aircraft.*
import com.example.airline.leasing.usecase.aircraft.AircraftExtractor
import java.time.OffsetDateTime
import java.util.*
import kotlin.random.Random

fun aircraftId() = AircraftId(Random.nextLong(1, 5000))

fun aircraft(
): Aircraft {
    return AircraftRestorer.restore(
            id = aircraftId(),
            manufacturer = manufacturer(),
            payload = payload(),
            releaseDate = releaseDate(),
            registrationNumber = registrationNumber(),
            contractNumber = contractNumber(),
            seats = seats(),
            version = version()
    )
}

fun manufacturer(): Manufacturer {
    val result = Manufacturer.from("Manufacturer ${Random.nextInt()}")
    check(result is Either.Right<Manufacturer>)
    return result.b
}

fun payload(value: Int = Random.nextInt(20, 5000)): AircraftPayload {
    val result = AircraftPayload.from(value)
    check(result is Either.Right<AircraftPayload>)
    return result.b
}

fun releaseDate(): OffsetDateTime {
    return OffsetDateTime.now()
}

fun contractNumber(): AircraftContractNumber {
    val result = AircraftContractNumber.from("ContractNumber ${Random.nextInt()}")
    check(result is Either.Right<AircraftContractNumber>)
    return result.b
}

fun registrationNumber(): AircraftRegistrationNumber {
    val result = AircraftRegistrationNumber.from("RegistrationNumber ${Random.nextInt()}")
    check(result is Either.Right<AircraftRegistrationNumber>)
    return result.b
}

fun seat(seat: String): Seat {
    val result = Seat.from(seat)
    check(result is Either.Right<Seat>)
    return result.b
}

fun seats(): Set<Seat> {
    return setOf(seat("1A"), seat("1B"), seat("1C"),
            seat("2A"), seat("2B"), seat("2C"),
            seat("3A"), seat("3B"), seat("3C"),
            seat("4A"), seat("4B"), seat("4C"))
}

fun version() = Version.new()

class TestAircraftExtractor : AircraftExtractor, LinkedHashMap<AircraftId, Aircraft>() {
    override fun getById(id: AircraftId) = this[id]

    override fun getAll() = values.toList()
}

fun flightId() = FlightId(Random.nextLong(1, 5000))

fun flightAircraftId() = com.example.airline.flight.domain.aircraft.AircraftId(Random.nextLong(1, 5000))

fun maintenanceFlightId(value: Long = Random.nextLong(20, 5000)): com.example.airline.maintenance.domain.FlightId {
    val result = com.example.airline.maintenance.domain.FlightId.from(value)
    check(result is Either.Right<com.example.airline.maintenance.domain.FlightId>)
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

fun flight(): Flight {
    return FlightRestorer.restore(
            id = flightId(),
            departureAirport = airport(),
            arrivalAirport = airport(),
            flightDate = flightDate(),
            aircraftId = flightAircraftId(),
            version = version()
    )
}

class TestFlightExtractor : FlightExtractor, LinkedHashMap<FlightId, Flight>() {
    override fun getById(id: FlightId) = this[id]

    override fun getAll() = values.toList()

    override fun getByAircraftId(aircraftId: com.example.airline.flight.domain.aircraft.AircraftId): List<Flight> {
        return this.values.filter { it.aircraftId == aircraftId }
    }
}
