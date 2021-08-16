package com.example.airline.flight.usecase.rules

import com.example.airline.flight.domain.aircraft.AircraftId
import com.example.airline.flight.domain.flight.AircraftIsAvailableOnTime
import com.example.airline.flight.usecase.flight.FlightExtractor
import java.time.OffsetDateTime

class AircraftIsAvailableOnTimeImpl(private val extractor: FlightExtractor) : AircraftIsAvailableOnTime {
    override fun check(aircraftId: AircraftId, datetime: OffsetDateTime): Boolean {
        val flights = extractor.getByAircraftId(aircraftId)
        return flights.none { it.flightDate == datetime }
    }
}