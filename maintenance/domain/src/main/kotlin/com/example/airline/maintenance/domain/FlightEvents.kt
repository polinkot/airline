package com.example.airline.maintenance.domain

import com.example.airline.common.types.base.DomainEvent

data class FlightRegisteredDomainEvent(val flightId: FlightId) : DomainEvent()
data class FlightDeparturedDomainEvent(val flightId: FlightId) : DomainEvent()
data class FlightArrivedDomainEvent(val flightId: FlightId) : DomainEvent()