package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.DomainEntity
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.error.BusinessError
import com.example.airline.maintenance.domain.FlightState.ARRIVED
import java.time.Duration

class Flight internal constructor(
        id: FlightId,
        val departureAirport: Airport,
        version: Version
) : DomainEntity<FlightId>(id, version) {
    var arrivalAirport: Airport? = null
    var duration: Duration = Duration.ZERO

    var state: FlightState = FlightState.REGISTERED
        internal set

    companion object {
        fun register(id: FlightId,
                     departureAirport: Airport
        ): Flight {
            return Flight(
                    id = id,
                    departureAirport = departureAirport,
                    version = Version.new()
            ).apply {
                addEvent(FlightRegisteredDomainEvent(flightId = this.id))
            }
        }
    }

    fun arrive(arrivalAirport: Airport, duration: Duration): Either<InvalidState, Unit> {
        if (!state.canChangeTo(ARRIVED)) {
            return InvalidState.left()
        }

        state = ARRIVED
        addEvent(FlightArrivedDomainEvent(id))
        this.arrivalAirport = arrivalAirport
        this.duration = duration
        return Unit.right()
    }
}

enum class FlightState(
        private val nextStates: Set<FlightState> = emptySet()
) {
    ARRIVED(nextStates = emptySet()),
    REGISTERED(nextStates = setOf(ARRIVED));

    fun canChangeTo(state: FlightState) = nextStates.contains(state)
}

object InvalidState : BusinessError