package com.jetbrains.kmpapp.utils

import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


sealed class Response<out R> {
    object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val errorMessage: String, val throwable: Throwable) : Response<Nothing>()
}

suspend fun <T> getResponse(invoke: suspend () -> T): Response<T> {
    return try {
        withContext(Dispatchers.IO) {
            val result = invoke()
            Napier.i("InResponse Success ${result.toString()}")
            Response.Success(result)
        }
    } catch (e: Exception) {
        Response.Error("ErrorInResponse", e)
    }
}