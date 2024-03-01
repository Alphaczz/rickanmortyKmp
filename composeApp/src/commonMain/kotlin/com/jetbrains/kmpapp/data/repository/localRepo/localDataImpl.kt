package com.jetbrains.kmpapp.data.repository.localRepo

import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.SharedDatabase
import com.jetbrains.kmpapp.model.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow

class localDataImpl(
    private val sharedDatabase: SharedDatabase,
) : ILocalData {
    override suspend fun getCharactersFromLocal(): Flow<List<Result?>> {
        Napier.i("getCharactersFromLocal(): ")
        return sharedDatabase.getAllItems()!!
    }

    override suspend fun getCharacterByIDFromLocal(id: Long): Flow<Result?> {
       return sharedDatabase.getItemById(id)!!
    }

    override suspend fun putCharatcersToLocal(list: List<Result?>) {
       sharedDatabase.addItems(list)
    }

    override suspend fun deleteAllData() {
       sharedDatabase.deleteALl()
    }
}