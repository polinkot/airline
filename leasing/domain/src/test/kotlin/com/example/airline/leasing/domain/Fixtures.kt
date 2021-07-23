package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Manufacturer
import com.example.airline.leasing.domain.seatingmap.*
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

fun seatingMapId() = SeatingMapId(Random.nextLong())

fun seatingMapName(): SeatingMapName {
    val result = SeatingMapName.from("SeatingMapName ${Random.nextInt()}")
    check(result is Either.Right<SeatingMapName>)
    return result.b
}

fun seatingRow(value: Int = Random.nextInt(20, 5000)): SeatingRow {
    val result = SeatingRow.from(value)
    check(result is Either.Right<SeatingRow>)
    return result.b
}

fun seatingSeat(): SeatingSeat {
    val result = SeatingSeat.from("SeatingSeat ${Random.nextInt()}")
    check(result is Either.Right<SeatingSeat>)
    return result.b
}

fun seating(
        row: SeatingRow = seatingRow(),
        seat: SeatingSeat = seatingSeat()
): Seating {
    return Seating(
            row = row,
            seat = seat
    )
}