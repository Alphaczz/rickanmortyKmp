package com.jetbrains.kmpapp.data.dataSource.localDataSource

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import kotlinx.coroutines.flow.Flow

interface ILocalData {
    suspend fun getCharactersFromLocal(): Flow<List<Result?>>
    fun getCharacterByIDFromLocal(id: Long):Flow<Result?>
    suspend fun putCharatcersToLocal(list:List<Result?>)
    suspend fun deleteAllData()
}