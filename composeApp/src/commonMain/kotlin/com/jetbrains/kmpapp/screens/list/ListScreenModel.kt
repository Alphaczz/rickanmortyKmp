package com.jetbrains.kmpapp.screens.list


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.kmpapp.data.RickAndMortyApi
import com.jetbrains.kmpapp.data.repository.IRepository
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.data.rickAndMortyRepository
import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.utils.Response
import com.jetbrains.kmpapp.utils.getResponse
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class ListScreenModel(private val repo: IRepository) : ScreenModel
{

    private val coroutineContext: CoroutineContext = Dispatchers.Default


//        val objects: StateFlow<List<RickAndMortyData>> =
//        rickAndMortyRepository.getObjects()
//            .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _data: MutableStateFlow<Response<List<RickAndMortyData?>>> =
        MutableStateFlow(Response.Loading)
    val data: StateFlow<Response<List<RickAndMortyData?>>> = _data

    init {
        fetchData()
    }
//    fun fetchData() {
//        screenModelScope.launch {
//            try {
//                val result = getResponse {
//                    repo.getRickAndMortyList()
//                }
//                _data.emit(result)
//            } catch (e: Exception) {
//                _data.emit(Response.Error("Error", e))
//            }
//        }
//    }







    fun fetchData() {
        CoroutineScope(coroutineContext).launch {
          val data= repo.getRickAndMortyList()

            _data.tryEmit(data)
        }
    }

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


