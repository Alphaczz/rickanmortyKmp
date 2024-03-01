package com.jetbrains.kmpapp.data.repository.localRepo.sqldelight

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.jetbrains.kmpapp.model.Location
import com.jetbrains.kmpapp.model.Origin
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedDatabase(databaseDriverFactory: DatabaseDriverFactory) {
    private val driver = databaseDriverFactory.createDriver()
    private val database = RickandMortyDb(driver)

    fun addItem(result: com.jetbrains.kmpapp.model.Result) {
        database?.rickandMortyDbQueries?.INSERT_RESULT (
            created = result.created,
            episode = result.episode.toString(),
            gender = result.gender,
            id = result.id.toLong(),
            image = result.image,
            location_name = result.location.name,
            location_url = result.location.url,
            name = result.name,
            origin_name = result.origin.name,
            origin_url = result.origin.url,
            species = result.species,
            status = result.status,
            type = result.type,
            url = result.url
        )
    }

    fun addItems(results: List<com.jetbrains.kmpapp.model.Result?>) {
        database?.transaction {
            results.forEach { addItem(it!!) }
        }
    }

    fun deleteALl(){
        database?.rickandMortyDbQueries?.DELETE_ALL_RESULTS()
    }
     fun getAllItems(): Flow<List<com.jetbrains.kmpapp.model.Result>>? =
        database?.rickandMortyDbQueries?.SELECT_ALL_RESULTS()
            ?.asFlow()
            ?.mapToList(Dispatchers.Main)?.map { resultList ->
                resultList.map { it.toDomainModel() }
            }


    fun getItemById(id: Long): Flow<com.jetbrains.kmpapp.model.Result?>? =
        database?.rickandMortyDbQueries?.SELECT_RESULT_BY_ID(id)
            ?.asFlow()
            ?.mapToOneOrNull(Dispatchers.IO)
            ?.map { it?.toDomainModel() }

//    private fun Result.toDomainModel(): com.jetbrains.kmpapp.model.Result {
//        return com.jetbrains.kmpapp.model.Result(
//            created = this.created,
//            episode = this.episode.split(","),
//            gender = this.gender,
//            id = this.id.toInt(),
//            image = this.image,
//            location = Location(this.location_name, this.location_url),
//            name = this.name,
//            origin = Origin(this.origin_name, this.origin_url),
//            species = this.species,
//            status = this.status,
//            type = this.type,
//            url = this.url
//        )
//    }
    private fun Result.toDomainModel(): com.jetbrains.kmpapp.model.Result {
        return com.jetbrains.kmpapp.model.Result(
            created = this.created,
            episode = this.episode.split(","),
            gender = this.gender,
            id = this.id.toInt(),
            image = this.image,
            location = Location(this.location_name, this.location_url),
            name = this.name,
            origin = Origin(this.origin_name, this.origin_url),
            species = this.species,
            status = this.status,
            type = this.type,
            url = this.url
        )
    }

}