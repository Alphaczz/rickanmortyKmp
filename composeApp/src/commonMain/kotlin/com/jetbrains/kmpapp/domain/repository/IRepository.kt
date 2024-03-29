package com.jetbrains.kmpapp.domain.repository

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.utils.Response
import kotlinx.coroutines.flow.Flow


interface IRepository {
    suspend fun getRickAndMortyList(): Flow<Response<List<Result?>>>
    suspend fun isSync():Boolean
    fun getByObjectId(id:Long):Flow<Result?>
    suspend fun iendpoint()
}