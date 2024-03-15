//package com.jetbrains.kmpapp.data.repository.remoteRepo
//
//import com.jetbrains.kmpapp.model.RickAndMortyData
//import com.jetbrains.kmpapp.utils.Response
//import com.jetbrains.kmpapp.utils.getResponse
//import io.github.aakira.napier.Napier
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.request.get
//import kotlinx.serialization.json.Json
//import kotlin.coroutines.cancellation.CancellationException
//
//class RemoteDataimpl(private val client: HttpClient):IRemoteData {
//    override suspend fun getCharactersFromApi(): Response<List<RickAndMortyData>> {
//        return try {
//            Napier.d(client.get("https://rickandmortyapi.com/api/character").body(), tag = "Data")
//
//            val response = client.get("https://rickandmortyapi.com/api/character")
//            val data = Json.decodeFromString<RickAndMortyData>(response.body()) // Try to deserialize as a single RickAndMortyData object
//            getResponse {
//                listOf(data)
//            }
//
//        } catch (e: Exception) {
//            if (e is CancellationException) throw e
//            e.printStackTrace()
//            Napier.d("Data", tag = "DatainCatch")
//            getResponse { emptyList() }
//        }
//    }
//
//    companion object {
//        private const val API_URL =
//            "https://rickandmortyapi.com/api/character"
//    }
//}
//
package com.jetbrains.kmpapp.dataSourceImpl.remoteDataSourceImpl

import ApiResult
import BaseDataSource
import com.jetbrains.kmpapp.data.network.KtorHttpClient
import com.jetbrains.kmpapp.data.dataSource.remoteDataSource.IRemoteData
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.utils.Response
import com.jetbrains.kmpapp.utils.getResponse
import io.ktor.http.Headers

class RemoteDataImpl(private val ktorHttpClient: KtorHttpClient) : IRemoteData,BaseDataSource(){

    override suspend fun postObjectToApi(): String {
        val jsonString = """{
   "name": "Apple MacBook Pro 16",
   "data": {
      "year": 2019,
      "price": 1849.99,
      "CPU model": "Intel Core i9",
      "Hard disk size": "1 TB"
   }
}"""

        //val json = Json.encodeToString(requestBody)
          // Napier.i(json, tag = "json")
        val headers = Headers.build {
            append("Content-Type", "application/json")
            // Add more headers if needed
        }
    // Add more headers if needed

        return try {
            // Make a POST request to the API endpoint with the provided request body and content type header
            val response = ktorHttpClient.post<String>("https://api.restful-api.dev/objects", jsonString,headers)
            return  response
        } catch (e: Exception) {
            // Handle any exceptions that may occur
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }
    override suspend fun getCharactersFromApi(onError: ((ApiResult<Any>) -> Unit)?): Response<List<RickAndMortyData>> {
        val apiResult = apiCall {
            getResponse {
                listOf(ktorHttpClient.get<RickAndMortyData>("$API_URL/character"))
            }
        }
        return if (apiResult.resultType == ApiResultType.SUCCESS) {
                 apiResult.data!!
        } else {
            onError?.invoke(ApiResult(null, apiResult.resultType, apiResult.error, resCode = apiResult.resCode))
             apiResult.data!!
        }
    }


    companion object {
        private const val API_URL = "https://rickandmortyapi.com/api"
    }
}

