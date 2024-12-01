import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
//SearchPreferncesManager.kt
// Extension pour initialiser DataStore
private val Context.dataStore by preferencesDataStore(name = "search_preferences")

class SearchPreferencesManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val SEARCH_TEXT_KEY = stringPreferencesKey("search_text")
    }

    suspend fun saveSearchText(text: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_TEXT_KEY] = text
        }
    }

    val searchText: Flow<String?>
        get() = dataStore.data.map { it[SEARCH_TEXT_KEY] }
}