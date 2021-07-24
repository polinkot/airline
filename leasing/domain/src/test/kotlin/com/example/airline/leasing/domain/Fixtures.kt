package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.seatmap.*
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

fun seatMapId() = SeatMapId(Random.nextLong())

fun seatMapName(): SeatMapName {
    val result = SeatMapName.from("SeatMapName ${Random.nextInt()}")
    check(result is Either.Right<SeatMapName>)
    return result.b
}

fun seatRow(value: Int = Random.nextInt(20, 5000)): Row {
    val result = Row.from(value)
    check(result is Either.Right<Row>)
    return result.b
}

fun seatLetter(): Letter {
    val result = Letter.from("SeatLetter ${Random.nextInt()}")
    check(result is Either.Right<Letter>)
    return result.b
}

fun seat(
        row: Row = seatRow(),
        letter: Letter = seatLetter()
): Seat {
    return Seat(
            row = row,
            letter = letter
    )
}