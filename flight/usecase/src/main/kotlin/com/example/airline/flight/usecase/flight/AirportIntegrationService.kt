package com.example.airline.flight.usecase.flight

import java.time.OffsetDateTime

interface AirportIntegrationService {
    fun checkTime(datetime: OffsetDateTime): Boolean
}
