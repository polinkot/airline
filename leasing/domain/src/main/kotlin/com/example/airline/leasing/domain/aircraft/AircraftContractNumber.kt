package com.example.airline.leasing.domain.aircraft

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.ValueObject
import com.example.airline.common.types.error.BusinessError

data class AircraftContractNumber internal constructor(val value: String) : ValueObject {
    companion object {
        fun from(contractNumber: String): Either<EmptyAircraftContractNumberError, AircraftContractNumber> =
                if (contractNumber.isNotBlank()) {
                    AircraftContractNumber(contractNumber).right()
                } else {
                    EmptyAircraftContractNumberError.left()
                }
    }
}

object EmptyAircraftContractNumberError : BusinessError