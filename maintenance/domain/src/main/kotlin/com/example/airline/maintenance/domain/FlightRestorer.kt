package com.example.airline.maintenance.domain

import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport

object FlightRestorer {

    fun restore(
            id: FlightId,
            departureAirport: Airport,
            arrivalAirport: Airport,
            duration: FlightDuration,
            version: Version
    ): Flight {
        return Flight(
                id = id,
                departureAirport = departureAirport,
                version = version
        ).apply {
            this.arrivalAirport = arrivalAirport
            this.duration = duration
            this.state = state
        }
    }
}