package com.example.airline.flight.usecase.flight

class GetFlightsUseCase(private val flightExtractor: FlightExtractor) : GetFlights {
    override fun execute(): List<FlightInfo> {
        return flightExtractor.getAll().map {
            FlightInfo(
                    id = it.id,
                    departureAirport = it.departureAirport,
                    arrivalAirport = it.arrivalAirport,
                    flightDate = it.flightDate
            )
        }.toList()
    }
}