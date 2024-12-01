package com.example.application_de_recherche_de_vols.data
//Favorite
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val departureCode: String,
    val destinationCode: String
)


