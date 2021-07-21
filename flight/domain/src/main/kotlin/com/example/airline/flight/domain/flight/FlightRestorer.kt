package com.example.airline.flight.domain.flight

import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.flight.domain.aircraft.Aircraft
import java.time.OffsetDateTime

@Suppress("LongParameterList")
object FlightRestorer {
    fun restore(
            id: FlightId,
            departureAirport: Airport,
            arrivalAirport: Airport,
            flightDate: OffsetDateTime,
            aircraft: Aircraft,
            version: Version
    ): Flight {
        return Flight(
                id = id,
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                flightDate = flightDate,
                aircraft = aircraft,
                version = version
        )
    }
}