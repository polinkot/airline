package com.example.airline.flight.domain.flight

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.example.airline.common.types.base.AggregateRoot
import com.example.airline.common.types.base.Version
import com.example.airline.common.types.common.Airport
import com.example.airline.common.types.error.BusinessError
import com.example.airline.flight.domain.aircraft.Aircraft
import com.example.airline.flight.domain.flight.FlightAnnounceError.AircraftIsNotAvailableOnTime
import com.example.airline.flight.domain.flight.FlightAnnounceError.AirportNotAllowFlightOnTime
import java.time.OffsetDateTime

@Suppress("LongParameterList")
class Flight internal constructor(
        id: FlightId,
        val departureAirport: Airport,
        val arrivalAirport: Airport,
        val flightDate: OffsetDateTime,
        val aircraft: Aircraft,
        version: Version
) : AggregateRoot<FlightId>(id, version) {
    companion object {
        fun create(idGenerator: FlightIdGenerator,
                   departureAirport: Airport,
                   arrivalAirport: Airport,
                   flightDate: OffsetDateTime,
                   aircraft: Aircraft,
                   aircraftIsAvailableOnTime: AircraftIsAvailableOnTime,
                   airportAllowsFlightOnTime: AirportAllowsFlightOnTime
        ): Either<FlightAnnounceError, Flight> {
            if (!aircraftIsAvailableOnTime.check(aircraft.id, flightDate)) {
                return AircraftIsNotAvailableOnTime.left()
            }

            if (!airportAllowsFlightOnTime.check(flightDate)) {
                return AirportNotAllowFlightOnTime.left()
            }

            return Flight(
                    id = idGenerator.generate(),
                    departureAirport = departureAirport,
                    arrivalAirport = arrivalAirport,
                    flightDate = flightDate,
                    aircraft = aircraft,
                    version = Version.new()
            ).apply {
                addEvent(FlightAnnouncedDomainEvent(flightId = this.id))
            }.right()
        }
    }
}

sealed class FlightAnnounceError : BusinessError {
    object AircraftIsNotAvailableOnTime : FlightAnnounceError()
    object AirportNotAllowFlightOnTime : FlightAnnounceError()
}
