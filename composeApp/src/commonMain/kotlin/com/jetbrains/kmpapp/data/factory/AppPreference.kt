import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface RickAndMortyPreferences {
    suspend fun getRickAndMortyData(): String?
    suspend fun saveRickAndMortyData(data: String)
}

internal class RickAndMortyPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : RickAndMortyPreferences {

    private companion object {
        private const val PREFS_TAG_KEY = "RickAndMortyPreferences"
        private const val RICK_AND_MORTY_DATA = "rickAndMortyData"
    }

    private val rickAndMortyDataKey = stringPreferencesKey("$PREFS_TAG_KEY$RICK_AND_MORTY_DATA")

    override suspend fun getRickAndMortyData(): String? {
        return dataStore.data
            .catch { exception ->
                // Handle error while reading preferences
                // For example, log the error or provide a default value
                exception.printStackTrace()
            }
            .map { preferences ->
                preferences[rickAndMortyDataKey]
            }
            .firstOrNull() // Provide a default value if flow completes without emitting
    }

    override suspend fun saveRickAndMortyData(data: String) {
        dataStore.edit { preferences ->
            preferences[rickAndMortyDataKey] = data
        }
    }
}
