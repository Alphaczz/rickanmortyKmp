package com.jetbrains.kmpapp.data.repository

import com.jetbrains.kmpapp.data.repository.localRepo.ILocalData
import com.jetbrains.kmpapp.data.repository.remoteRepo.IRemoteData
import com.jetbrains.kmpapp.data.rickAndMortyRepository
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.utils.Response
import com.jetbrains.kmpapp.utils.getResponse
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val RemoteDataSource: IRemoteData,
    private val LocalDataSource: ILocalData,
   // private val preferenceDataStore: PreferenceDataStore
) : IRepository {
    override suspend fun getRickAndMortyList(): Flow<Response<List<Result?>>> =
        singleSourceOfTruth(
            getLocalData = { getDataFromLocalDataSource() },
            getRemoteData = {
                getDataFromRemote()
            },
            saveDataToLocal = {
               putDataInLocal(it)
            }
        )


    private suspend fun getDataFromRemote(): List<Result?> {
        Napier.i("getDataFromRemote()")
        val data = RemoteDataSource.getCharactersFromApi()
        when(data){
            is Response.Error -> TODO()
            Response.Loading -> TODO()
            is Response.Success ->
            {
               return data.data[0].results!!
            }
        }
        //deleteAll()

        // Save data to local

    }

    private suspend fun getDataFromLocalDataSource(): List<Result> {
        val resultList = mutableListOf<Result>()

        try {
            LocalDataSource.getCharactersFromLocal().collect { result ->
                result?.let {
                    Napier.e("Data in Local: ${result[0]?.name}")
                    resultList.addAll(it.filterNotNull())
                    Napier.e("Data in Local 2: ${resultList[0].name}")
                }
            }
        } catch (e: Exception) {
            Napier.e("Error fetching data from local data source: $e")
        }

        Napier.e("Data in Local3: ${resultList.joinToString { it.name }}")

        return resultList
    }



    private suspend fun putDataInLocal(list: List<Result?>) {
        Napier.i("Data in Local saved")
        LocalDataSource.putCharatcersToLocal(list)
    }

    private suspend fun deleteAll() {
        LocalDataSource.deleteAllData()
    }

//    override suspend fun getRickAndMortyList(): Response<List<RickAndMortyData?>> {
//        var data:Response<List<RickAndMortyData?>>?=null
//        data=RemoteDataSource.getCharactersFromApi()
//        return data
//    }


}