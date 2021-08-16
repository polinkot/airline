package com.example.airline.leasing.usecase.aircraft

import com.example.airline.leasing.domain.aircraft.Aircraft

interface AircraftPersister {
    fun save(aircraft: Aircraft)
}