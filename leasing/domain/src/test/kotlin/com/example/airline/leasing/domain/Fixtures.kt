package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import kotlin.random.Random

fun version() = Version.new()

fun aircraftId() = AircraftId(Random.nextLong())

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

fun seat(seat : String): Seat {
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