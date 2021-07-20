package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class AircraftManufacturer internal constructor(val value: String) : ValueObject {
    companion object {
        fun from(name: String): Either<EmptyAircraftManufacturerError, AircraftManufacturer> =
                if (name.isNotBlank()) {
                    AircraftManufacturer(name).right()
                } else {
                    EmptyAircraftManufacturerError.left()
                }
    }
}

object EmptyAircraftManufacturerError : BusinessError