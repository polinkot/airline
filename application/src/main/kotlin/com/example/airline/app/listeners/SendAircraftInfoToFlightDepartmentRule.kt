package com.example.airline.app.listeners

import com.example.airline.app.event.DomainEventListener
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraft
import com.example.airline.flight.usecase.aircraft.ReceiveInfoAircraftRequest
import com.example.airline.leasing.domain.aircraft.AircraftCreatedDomainEvent
import com.example.airline.leasing.usecase.aircraft.AircraftExtractor

class SendAircraftInfoToFlightDepartmentRule(
        private val leasingAircraftExtractor: AircraftExtractor,
        private val receiveInfoAircraft: ReceiveInfoAircraft
) : DomainEventListener<AircraftCreatedDomainEvent> {
    override fun eventType() = AircraftCreatedDomainEvent::class

    override fun handle(event: AircraftCreatedDomainEvent) {
        val aircraft = leasingAircraftExtractor.getById(event.aircraftId)
        checkNotNull(aircraft) {
            "Aircraft #${event.aircraftId} not found"
        }

        val request = ReceiveInfoAircraftRequest(id = aircraft.id.value, manufacturer = aircraft.manufacturer.value,
                seatsCount = aircraft.seats.size)
        receiveInfoAircraft.execute(request).mapLeft {
            error("Cannot receive aircraft info id = #${aircraft.id} in flight department: $it")
        }
    }
}