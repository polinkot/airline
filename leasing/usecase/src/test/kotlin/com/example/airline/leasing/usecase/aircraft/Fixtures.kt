package com.example.airline.leasing.usecase.aircraft

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.aircraft.*
import java.time.OffsetDateTime
import java.util.*
import kotlin.random.Random

fun aircraftId() = AircraftId(Random.nextLong())

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

fun seatParams(seats: Set<Seat>): Set<String> {
    return seats.map { it.value }.toSet()
}

fun version() = Version.new()

class TestAircraftPersister : HashMap<AircraftId, Aircraft>(), AircraftPersister {
    override fun save(aircraft: Aircraft) {
        this[aircraft.id] = aircraft
    }
}

class TestAircraftExtractor : AircraftExtractor, LinkedHashMap<AircraftId, Aircraft>() {
    override fun getById(id: AircraftId) = this[id]

    override fun getAll() = values.toList()
}