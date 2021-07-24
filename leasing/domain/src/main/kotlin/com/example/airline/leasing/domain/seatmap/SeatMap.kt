package com.example.airline.leasing.domain.seatmap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.error.BusinessError

/**
 * Схема расстановки кресел. Может быть одна и та же у самолётов одного типа.
 * Сделала отдельным классом.
 */
class SeatMap internal constructor(
        id: SeatMapId,
        val name: SeatMapName,
        val seats: Set<Seat>,
        version: Version
) : AggregateRoot<SeatMapId>(id, version) {

    companion object {
        fun create(idGenerator: SeatMapIdGenerator,
                   name: SeatMapName,
                   seats: Set<Seat>
        ): Either<EmptySeatMap, SeatMap> {
            return if (seats.isNotEmpty()) {
                SeatMap(
                        id = idGenerator.generate(),
                        name = name,
                        seats = seats,
                        version = Version.new()
                ).right()
            } else {
                EmptySeatMap.left()
            }
        }
    }
}

object EmptySeatMap : BusinessError