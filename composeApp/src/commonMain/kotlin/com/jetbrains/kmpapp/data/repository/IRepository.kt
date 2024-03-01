package com.jetbrains.kmpapp.data.repository

import com.jetbrains.kmpapp.model.Result
import com.jetbrains.kmpapp.model.RickAndMortyData
import com.jetbrains.kmpapp.utils.Response
import kotlinx.coroutines.flow.Flow


interface IRepository {
    suspend fun getRickAndMortyList(): Response<List<RickAndMortyData?>>
    //Response<List<Result?>>>
}