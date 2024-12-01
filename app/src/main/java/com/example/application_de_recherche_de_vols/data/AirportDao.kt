
package com.example.application_de_recherche_de_vols.data
//AiportDao.kt
import androidx.room.Dao
import androidx.room.Query

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE name LIKE '%' || :query || '%' OR iataCode LIKE '%' || :query || '%'")
    fun searchAirports(query: String): List<Airport>

    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): List<Airport>
}
