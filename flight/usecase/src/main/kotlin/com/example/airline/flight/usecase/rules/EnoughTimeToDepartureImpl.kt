package com.example.airline.flight.usecase.rules

import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.EnoughTimeToDeparture
import com.example.airline.flight.usecase.flight.FlightExtractor
import java.time.OffsetDateTime

class EnoughTimeToDepartureImpl(private val extractor: FlightExtractor) : EnoughTimeToDeparture {
    override fun check(flightId: FlightId, hours: Long): Boolean {
        val flight = extractor.getById(flightId)
        return flight != null && OffsetDateTime.now().isBefore(flight.flightDate.minusHours(hours))
    }
}