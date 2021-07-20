package com.example.airline.leasing.domain.seatingmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.error.BusinessError

class SeatingMap internal constructor(
        id: SeatingMapId,
        val name: SeatingMapName,
        val seatings: Set<Seating>,
        version: Version
) : AggregateRoot<SeatingMapId>(id, version) {

    companion object {
        fun create(idGenerator: SeatingMapIdGenerator,
                   name: SeatingMapName,
                   seatings: Set<Seating>
        ): Either<EmptySeatingMap, SeatingMap> {
            return if (seatings.isNotEmpty()) {
                SeatingMap(
                        id = idGenerator.generate(),
                        name = name,
                        seatings = seatings,
                        version = Version.new()
                ).right()
            } else {
                EmptySeatingMap.left()
            }
        }
    }
}

object EmptySeatingMap : BusinessError