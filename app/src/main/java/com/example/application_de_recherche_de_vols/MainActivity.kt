package com.example.application_de_recherche_de_vols

import SearchPreferencesManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.application_de_recherche_de_vols.data.AirportDao
import com.example.application_de_recherche_de_vols.data.Favorite
//import com.example.application_de_recherche_de_vols.data.FavoriteDao
import com.example.application_de_recherche_de_vols.data.FlightDatabase
import com.example.application_de_recherche_de_vols.ui.theme.Application_de_recherche_de_volsTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.application_de_recherche_de_vols.data.FakeAirportDao
//import com.example.application_de_recherche_de_vols.data.FakeFavoriteDao



class MainActivity : ComponentActivity() {

    private lateinit var db: FlightDatabase
    private lateinit var airportDao: AirportDao
   // private lateinit var favoriteDao: FavoriteDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Copier la base de données depuis les assets
        DatabaseHelper.copyDatabaseFromAssets(this, "flight_search.db")

        // Initialiser la base de données Room
        db = Room.databaseBuilder(
            applicationContext,
            FlightDatabase::class.java,
            "flight_database"
        ).build()

        airportDao = db.airportDao()
       // favoriteDao = db.favoriteDao()

        setContent {
            Application_de_recherche_de_volsTheme {
                MainScreen(
                    airportDao = airportDao,
                   // favoriteDao = favoriteDao
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Application_de_recherche_de_volsTheme {
        MainScreen(
            airportDao = FakeAirportDao(), // Implémentez une version factice pour les tests
            //favoriteDao = FakeFavoriteDao(),
            //lifecycleScope = TODO(),
        )
    }
}
