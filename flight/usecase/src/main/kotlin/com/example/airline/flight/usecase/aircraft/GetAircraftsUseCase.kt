package com.example.airline.flight.usecase.aircraft

class GetAircraftsUseCase(private val aircraftExtractor: AircraftExtractor) : GetAircrafts {
    override fun execute(): List<AircraftInfo> {
        return aircraftExtractor.getAll().map {
            AircraftInfo(
                    id = it.id,
                    manufacturer = it.manufacturer,
                    seatsCount = it.seatsCount
            )
        }.toList()
    }
}