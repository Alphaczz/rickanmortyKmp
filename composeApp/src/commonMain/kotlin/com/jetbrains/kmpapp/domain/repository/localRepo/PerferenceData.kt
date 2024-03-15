package com.jetbrains.kmpapp.domain.repository.localRepo

import com.jetbrains.kmpapp.model.RickAndMortyData

//
 interface PreferenceData {
    suspend fun put(key: String,  value: List<RickAndMortyData>)
    fun getListData(key: String): List<RickAndMortyData>?
}