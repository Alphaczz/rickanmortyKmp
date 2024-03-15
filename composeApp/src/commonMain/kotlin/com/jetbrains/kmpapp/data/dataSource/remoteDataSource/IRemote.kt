package com.jetbrains.kmpapp.data.dataSource.remoteDataSource

import ApiResult
import com.jetbrains.kmpapp.data.RickAndMortyApi
import com.jetbrains.kmpapp.data.network.KtorHttpClient
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.utils.Response
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

interface IRemoteData {
  suspend fun getCharactersFromApi( onError: ((ApiResult<Any>) -> Unit)?):  Response<List<RickAndMortyData>>
  suspend fun postObjectToApi(): String
}
