package com.example.airline.flight.usecase.rules

import com.example.airline.flight.domain.flight.FlightId
import com.example.airline.flight.domain.ticket.FlightIsAnnounced
import com.example.airline.flight.usecase.flight.FlightExtractor

class FlightIsAnnouncedImpl(private val extractor: FlightExtractor) : FlightIsAnnounced {
    override fun check(flightId: FlightId): Boolean {
        val flight = extractor.getById(flightId)
        return flight != null
    }
}