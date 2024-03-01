package com.jetbrains.kmpapp.data.repository.remoteRepo

import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.utils.Response
import com.jetbrains.kmpapp.utils.getResponse
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class RemoteDataimpl(private val client: HttpClient):IRemoteData {
    override suspend fun getCharactersFromApi(): Response<List<RickAndMortyData>> {
        return try {
            Napier.d(client.get("https://rickandmortyapi.com/api/character").body(), tag = "Data")

            val response = client.get("https://rickandmortyapi.com/api/character")
            val data = Json.decodeFromString<RickAndMortyData>(response.body()) // Try to deserialize as a single RickAndMortyData object
            getResponse {
                listOf(data)
            }

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Napier.d("Data", tag = "DatainCatch")
            getResponse { emptyList() }
        }
    }
    companion object {
        private const val API_URL =
            "https://rickandmortyapi.com/api/character"
    }
}
