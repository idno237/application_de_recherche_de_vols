
package com.example.application_de_recherche_de_vols.data

class FakeAirportDao : AirportDao {
    override fun searchAirports(query: String): List<Airport> {
        // Retourne des données simulées pour le test
        return listOf(
            Airport(1, "JFK", "John F. Kennedy International Airport", 50000),
            Airport(2, "LAX", "Los Angeles International Airport", 45000),
            Airport(3, "ORD", "Chicago O'Hare International Airport", 40000)
        ).filter {
            it.name.contains(query, ignoreCase = true) || it.iataCode.contains(query, ignoreCase = true)
        }
    }

    override fun getAllAirports(): List<Airport> {
        // Retourne une liste d'aéroports simulés
        return listOf(
            Airport(1, "JFK", "John F. Kennedy International Airport", 50000),
            Airport(2, "LAX", "Los Angeles International Airport", 45000),
            Airport(3, "ORD", "Chicago O'Hare International Airport", 40000)
        )
    }
}
