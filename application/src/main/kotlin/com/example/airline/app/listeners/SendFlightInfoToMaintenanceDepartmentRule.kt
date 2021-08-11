package com.example.airline.app.listeners

import com.example.airline.app.event.DomainEventListener
import com.example.airline.flight.domain.flight.FlightAnnouncedDomainEvent
import com.example.airline.flight.usecase.flight.FlightExtractor
import com.example.airline.maintenance.usecase.RegisterFlight
import com.example.airline.maintenance.usecase.RegisterFlightRequest

class SendFlightInfoToMaintenanceDepartmentRule(
        private val flightExtractor: FlightExtractor,
        private val registerFlight: RegisterFlight
) : DomainEventListener<FlightAnnouncedDomainEvent> {
    override fun eventType() = FlightAnnouncedDomainEvent::class

    override fun handle(event: FlightAnnouncedDomainEvent) {
        val flight = flightExtractor.getById(event.flightId)
        checkNotNull(flight) {
            "Flight #${event.flightId} not found"
        }

        RegisterFlightRequest.from(id = flight.id.value, departureAirport = flight.departureAirport.value)
                .map { request ->
                    registerFlight.execute(request)
                }
                .mapLeft {
                    error("Cannot receive flight info id = #${flight.id} in maintenance department: $it")
                }
    }
}