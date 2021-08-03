package com.example.airline.maintenance.domain

class RegisterFlightUseCase(
        private val flightPersister: FlightPersister
) : RegisterFlight {
    override fun execute(request: RegisterFlightRequest): FlightId {
        val flight = Flight.register(
                id = request.id,
                departureAirport = request.departureAirport
        )
        flightPersister.save(flight)

        return flight.id
    }
}