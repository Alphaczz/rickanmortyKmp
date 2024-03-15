package com.jetbrains.kmpapp.screens.detail

import cafe.adriel.voyager.core.model.ScreenModel
import com.jetbrains.kmpapp.domain.repository.IRepository
import com.jetbrains.kmpapp.data.rickAndMortyRepository
import com.jetbrains.kmpapp.model.Result
import kotlinx.coroutines.flow.Flow

class DetailScreenModel(private val repository: IRepository
) : ScreenModel {
     fun getObject(objectId: Long): Flow<Result?> =
        repository.getByObjectId(objectId)
}
