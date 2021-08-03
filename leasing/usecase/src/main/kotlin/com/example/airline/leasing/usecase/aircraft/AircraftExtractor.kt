package com.example.airline.leasing.usecase.aircraft

import com.example.airline.leasing.domain.aircraft.Aircraft
import com.example.airline.leasing.domain.aircraft.AircraftId

interface AircraftExtractor {

    fun getById(id: AircraftId): Aircraft?

    fun getAll(): List<Aircraft>
}