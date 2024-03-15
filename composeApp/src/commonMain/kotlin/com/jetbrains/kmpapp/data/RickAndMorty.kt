package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.model.RickAndMortyData
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.logging.Logger
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.json.Json

//Not in use
interface RickAndMortyApi {
    suspend fun getData(): List<RickAndMortyData>
}

class RickAndMortyApiClient(private val client: HttpClient) : RickAndMortyApi {
    companion object {
        private const val API_URL =
            "https://rickandmortyapi.com/api/character"
    }

    override suspend fun getData(): List<RickAndMortyData> {
        return try {
            Napier.d(client.get(API_URL).body(), tag = "NoData")
            val response = client.get(API_URL)
            val data = Json.decodeFromString<RickAndMortyData>(response.body()) // Try to deserialize as a single RickAndMortyData object
            listOf(data)

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Napier.d("Data", tag = "NoData")
            emptyList()
        }
    }
}
