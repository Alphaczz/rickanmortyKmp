package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
//not using
interface RickAndMortyStorage {

    fun getObjectById(objectId: Int): Flow<Result?>
    fun getObjects(): Flow<List<RickAndMortyData>>
    suspend fun saveObjects(newObjects: List<RickAndMortyData>)
}

class InMemoryRickAndMortyStorage : RickAndMortyStorage {
    private val storedObjects = MutableStateFlow(emptyList<RickAndMortyData>())

    override fun getObjectById(objectId: Int): Flow<Result?> {
        return storedObjects.map { objects ->
            objects.flatMap { it.results !!}
                .find { it.id == objectId }
        }
    }


    override fun getObjects(): Flow<List<RickAndMortyData>> = storedObjects
    override suspend fun saveObjects(newObjects: List<RickAndMortyData>){
        storedObjects.value = newObjects
    }


}
