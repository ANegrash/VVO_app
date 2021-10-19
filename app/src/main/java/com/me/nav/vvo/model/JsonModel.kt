package com.me.nav.vvo.model

class JsonModel {
    private var flights: List<FlightModel>? = null

    fun JsonModel(
        flights: List<FlightModel>?
    ) {
        this.flights = flights
    }

    fun getFlights(): List<FlightModel>? {
        return flights
    }
}