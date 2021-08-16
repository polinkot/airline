package com.example.airline.maintenance.usecase

class GetFlightsUseCase(private val flightExtractor: FlightExtractor) : GetFlights {
    override fun execute(): List<FlightInfo> {
        return flightExtractor.getAll().map {
            FlightInfo(
                    id = it.id,
                    departureAirport = it.departureAirport,
                    arrivalAirport = it.arrivalAirport,
                    state = it.state
            )
        }.toList()
    }
}