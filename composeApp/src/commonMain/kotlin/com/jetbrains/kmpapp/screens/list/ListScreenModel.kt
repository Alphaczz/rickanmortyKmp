package com.jetbrains.kmpapp.screens.list


import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.domain.repository.IRepository
import com.jetbrains.kmpapp.data.dataSource.remoteDataSource.IRemoteData
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.utils.Response
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListScreenModel(private val repo: IRepository, private val remote: IRemoteData) : ScreenModel
{

    private val coroutineContext: CoroutineContext = Dispatchers.Default
    private val refreshIntervalMillis: Long = 60000
//    private val _data: MutableStateFlow<Response<List<RickAndMortyData?>>> =
//        MutableStateFlow(Response.Loading)
//    val data: StateFlow<Response<List<RickAndMortyData?>>> = _data
//        val objects: StateFlow<List<RickAndMortyData>> =
//        rickAndMortyRepository.getObjects()
//            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _data: MutableStateFlow<Response<List<Result?>>> =
        MutableStateFlow(Response.Loading)
    val data: StateFlow<Response<List<Result?>>> = _data

    init {
        fetchData()
        getdata()
        getDataFromIEndPoint()
        startAutoRefresh()
    }
    fun getDataFromIEndPoint(){
        CoroutineScope(coroutineContext).launch {
            val check= repo.iendpoint()
           // Napier.e((check?:"" ) +"endpointimpl")
//            when (check){
//                is Response.Error -> TODO()
//                Response.Loading -> TODO()
//                is Response.Success -> {
//                    Napier.e((check.data.get(0).results?.get(0)?.name ?:"" ) +"endpointimpl")
//                }
//
//            }
        }
    }
    fun getdata(){
        CoroutineScope(coroutineContext).launch {
            val check= remote.postObjectToApi()
            Napier.e((check?:"" ) +"endpointimpl")
//            when (check){
//                is Response.Error -> TODO()
//                Response.Loading -> TODO()
//                is Response.Success -> {
//                    Napier.e((check.data.get(0).results?.get(0)?.name ?:"" ) +"endpointimpl")
//                }
//
//            }
        }
    }
    fun fetchData() {
        CoroutineScope(coroutineContext).launch {
            try {
                val data = repo.getRickAndMortyList()
                data.collect { result ->
                    _data.value = result
                    Napier.e(data.toString()+"fetchdata")
                }

            } catch (e: Exception) {
                _data.value = Response.Error("Error", e)
            }
        }
    }
    private fun startAutoRefresh() {
        CoroutineScope(coroutineContext).launch {
            Napier.i("Auto Refresh started")
            while (true) {
                Napier.i("Refreshing data...")
                refresh()
                delay(refreshIntervalMillis)
            }
        }
    }

    fun refresh(){
     CoroutineScope(coroutineContext).launch {
         try {
             val isSync=repo.isSync()
             if (isSync){
                 Napier.i("Change in Data", tag = "IsSync")
                 repo.getRickAndMortyList()
                 data.collect { result ->
                     _data.value = result
                     Napier.e(data.toString()+"refreshed data")
                 }
//                remote.getCharactersFromApi(onError = {})

             }else{
                 Napier.i("No Change in Data", tag = "IsSync")
             }
         } catch (e: Exception) {
             _data.value = Response.Error("Error", e)
         }
     }
 }






//    fun fetchData() {
//        CoroutineScope(coroutineContext).launch {
//          val data= repo.getRickAndMortyList()
//
//            _data.tryEmit(data)
//        }
//    }

//fun fetchData() {
//        CoroutineScope(coroutineContext) .launch {
//            try {
//                val data = api.getData()
//
//            } catch (e: Exception) {
//                Napier.d {"No Data i"/* data.get(0).results?.get(0)!!.name*/ }
//            }
//        }
//    }
//
}


