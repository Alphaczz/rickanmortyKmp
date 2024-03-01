package com.jetbrains.kmpapp.data.repository.localRepo

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import kotlinx.coroutines.flow.Flow

interface ILocalData {
    suspend fun getCharactersFromLocal(): Flow<List<Result?>>
    suspend fun getCharacterByIDFromLocal(id: Long):Flow<Result?>
    suspend fun putCharatcersToLocal(list:List<Result?>)
    suspend fun deleteAllData()
}