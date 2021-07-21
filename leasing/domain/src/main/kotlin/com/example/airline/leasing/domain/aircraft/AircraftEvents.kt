package com.example.airline.leasing.domain.aircraft

import com.example.airline.common.types.base.DomainEvent

data class AircraftCreatedDomainEvent(val aircraftId: AircraftId) : DomainEvent()