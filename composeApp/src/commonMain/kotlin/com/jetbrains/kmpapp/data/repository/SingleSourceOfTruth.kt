package com.jetbrains.kmpapp.data.repository



import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.utils.Response
import com.jetbrains.kmpapp.utils.getResponse
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
//
//internal suspend fun singleSourceOfTruth(
//    getLocalData: suspend () -> List<Result?>,
//    getRemoteData: suspend () -> List<Result?>,
//    saveDataToLocal: suspend (List<Result?>) -> Unit,
//): Response<Flow<List<Result?>>> = try {
//    Napier.e("Success in singleSourceOfTruth")
//   // val remoteData = getRemoteData()
//    val localData = getLocalData()
//    if (localData.isNullOrEmpty()) {
//        val remoteData = getRemoteData()
//        if (remoteData.isNotEmpty()) {
//            Napier.e("remote success in singleSourceOfTruth:😶‍🌫️😶‍🌫️")
//            //saveDataToLocal(remoteData)
//            Response.Success(flow { emit(remoteData) })
//        } else {
//            Response.Error("No data available locally or remotely", RuntimeException("No data available"))
//        }
//    } else {
//        Napier.e("local success in singleSourceOfTruth:😶‍🌫️😶‍🌫️")
//        Response.Success(flow { emit(localData) })
//    }
//} catch (e: Exception) {
//    Napier.e("Error in singleSourceOfTruth: $e")
//    Response.Error("Error in fetching data", e)
//}
internal fun  singleSourceOfTruth (
    getLocalData: suspend () -> List<Result?>,
    getRemoteData: suspend () -> List<Result?>,
    saveDataToLocal: suspend (List<Result?>) -> Unit,
): Flow<Response<List<Result?>>> = flow {
    val localData = getResponse { getLocalData() }
    var locData:List<Result?>?=null
    when(localData){
        is Response.Error -> TODO()
        Response.Loading -> TODO()
        is Response.Success -> {
            locData=localData.data!!
        }
    }
    if ( locData.isNotEmpty()) {
        Napier.d("InLocalSSOt")
        emit(localData)
    } else {
        val remoteData = getResponse { getRemoteData() }
        if (remoteData is Response.Success) {
            if (remoteData.data.isNotEmpty()) {
                saveDataToLocal(remoteData.data)
                val localDataUpdated = getResponse { getLocalData() }
                emit(localDataUpdated)
            }
        } else {
            emit(Response.Error("Error", (remoteData as Response.Error).throwable))
        }
    }
}