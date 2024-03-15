package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
//not using
class rickAndMortyRepository(
    private val rickAndMortyApi: RickAndMortyApi,
    private val rickAndMortyStorage: RickAndMortyStorage,
//    private val preferenceDataStore: PreferenceData,

    ) {
    private val scope = CoroutineScope(SupervisorJob())

    fun initialize() {
        scope.launch {
            refresh()
        }
    }

    suspend fun refresh() {
        rickAndMortyStorage.saveObjects(rickAndMortyApi.getData()!!)
//        preferenceDataStore.put("ListData",rickAndMortyApi.getData()!!)
//        Napier.i(preferenceDataStore.getListData("ListData")?.get(0)?.results?.get(0)!!.name!!)
    }

    fun getObjects(): Flow<List<RickAndMortyData>> = rickAndMortyStorage.getObjects()

    fun getObjectById(objectId: Int): Flow<Result?> = rickAndMortyStorage.getObjectById(objectId)
}
