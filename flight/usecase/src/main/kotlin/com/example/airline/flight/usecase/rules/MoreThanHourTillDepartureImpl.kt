package com.example.airline.flight.usecase.rules

import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.MoreThanHourTillDeparture
import com.example.airline.flight.usecase.flight.FlightExtractor
import java.time.OffsetDateTime

class MoreThanHourTillDepartureImpl(private val extractor: FlightExtractor) : MoreThanHourTillDeparture {
    override fun check(flightId: FlightId): Boolean {
        val flight = extractor.getById(flightId)
        return flight != null && OffsetDateTime.now().isBefore(flight.flightDate.minusHours(1))
    }
}