package com.example.application_de_recherche_de_vols.data
//FlightDatabase
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1, exportSchema = false)
abstract class FlightDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
   // abstract fun favoriteDao(): FavoriteDao
}

