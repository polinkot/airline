package com.example.airline.flight.domain.flight

import com.example.airline.common.types.base.DomainEvent

data class FlightAnnouncedDomainEvent(val flightId: FlightId) : DomainEvent()