package com.example.airline.flight.domain.aircraft

import com.example.airline.common.types.base.DomainEvent

data class AircraftInfoReceivedDomainEvent(val aircraftId: AircraftId) : DomainEvent()