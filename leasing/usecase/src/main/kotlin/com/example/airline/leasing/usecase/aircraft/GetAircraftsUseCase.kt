package com.example.airline.leasing.usecase.aircraft

class GetAircraftsUseCase(private val aircraftExtractor: AircraftExtractor) : GetAircrafts {
    override fun execute(): List<AircraftInfo> {
        return aircraftExtractor.getAll().map {
            AircraftInfo(
                    id = it.id,
                    manufacturer = it.manufacturer,
                    releaseDate = it.releaseDate,
                    registrationNumber = it.registrationNumber,
                    contractNumber = it.contractNumber
            )
        }.toList()
    }
}