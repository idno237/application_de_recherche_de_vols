package com.example.application_de_recherche_de_vols

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place // Icône de lieu
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search // Icône de recherche
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.application_de_recherche_de_vols.data.Airport
import com.example.application_de_recherche_de_vols.data.AirportDao
import com.example.application_de_recherche_de_vols.data.Favorite
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    airportDao: AirportDao,
) {
    var searchQuery by remember { mutableStateOf("") }
    var airports by remember { mutableStateOf<List<Airport>>(emptyList()) }
    var favorites by remember { mutableStateOf<List<Favorite>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            airports = airportDao.getAllAirports()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF0F4F8)) // Soft background color
    ) {
        // Application Icon (Icône de lieu)
        Icon(
            imageVector = Icons.Default.Place, // Icône de lieu
            contentDescription = "Application Icon",
            modifier = Modifier.size(64.dp).align(Alignment.CenterHorizontally)
        )

        // Title
        Text(
            text = "Recherche d'Aéroports",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE), // Title color
                fontSize = 24.sp
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Search Bar with Search Icon
        SearchBar(
            query = searchQuery,
            onQueryChange = { query -> searchQuery = query }
        )

        val filteredAirports = if (searchQuery.isEmpty()) {
            airports
        } else {
            airports.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.iataCode.contains(searchQuery, ignoreCase = true)
            }
        }

        if (filteredAirports.isEmpty()) {
            Text(
                text = "Aucun aéroport trouvé",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                items(filteredAirports) { airport ->
                    AirportItem(
                        airport = airport,
                        isFavorite = favorites.any { it.departureCode == airport.iataCode },
                        onFavoriteClick = { isFavorite ->
                            coroutineScope.launch {
                                if (isFavorite) {
                                    // Ajouter aux favoris
                                    Favorite(
                                        departureCode = airport.iataCode,
                                        destinationCode = "DEST" // Placeholder
                                    )
                                } else {
                                    // Retirer des favoris
                                    val favoriteToRemove = favorites.find {
                                        it.departureCode == airport.iataCode
                                    }
                                    favoriteToRemove?.let {
                                        //favoriteDao.removeFavorite(it)
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Rechercher un aéroport") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFBBDEFB), shape = MaterialTheme.shapes.large) // Soft blue background
            .clip(MaterialTheme.shapes.large), // Oval shape
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, // Icône de recherche
                contentDescription = "Search Icon"
            )
        }
    )
}

@Composable
fun AirportItem(
    airport: Airport,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${airport.iataCode} - ${airport.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Passagers : ${airport.passengers}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { onFavoriteClick(!isFavorite) }) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    },
                    contentDescription = null
                )
            }
        }
    }
}
