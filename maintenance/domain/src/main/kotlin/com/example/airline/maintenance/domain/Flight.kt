package com.example.airline.maintenance.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.DomainEntity
import com.example.airline.common.types.base.DomainEvent
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.error.BusinessError
import java.time.Duration

data class FlightId(val value: Long)

class Flight internal constructor(
        id: FlightId,
        val departureAirport: Airport,
        version: Version
) : DomainEntity<FlightId>(id, version) {
    var arrivalAirport: Airport? = null
    var duration: Duration = Duration.ZERO

    var state: FlightState = FlightState.IDLE
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

    fun depart() = changeState(FlightState.DEPARTURED, FlightDeparturedDomainEvent(id))

    fun arrive(arrivalAirport: Airport, duration: Duration) {
        if (state == FlightState.ARRIVED) {
            return
        }

        if (changeState(FlightState.ARRIVED, FlightArrivedDomainEvent(id)).isRight()) {
            this.arrivalAirport = arrivalAirport
            this.duration = duration
        }
    }

    private fun changeState(newState: FlightState, event: DomainEvent): Either<InvalidState, Unit> {
        return when {
            state == newState -> Unit.right()
            state.canChangeTo(newState) -> {
                state = newState
                addEvent(event)
                Unit.right()
            }
            else -> InvalidState.left()
        }
    }
}

enum class FlightState(
        private val nextStates: Set<FlightState> = emptySet()
) {
    ARRIVED(nextStates = emptySet()),
    DEPARTURED(nextStates = setOf(ARRIVED)),
    IDLE(nextStates = setOf(DEPARTURED));

    fun canChangeTo(state: FlightState) = nextStates.contains(state)
}

object InvalidState : BusinessError