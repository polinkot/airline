package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.seatingmap.SeatingMapId
import kotlin.random.Random

fun aircraftId() = AircraftId(Random.nextLong())

fun seatingMapId() = SeatingMapId(Random.nextLong())

fun version() = Version.new()

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