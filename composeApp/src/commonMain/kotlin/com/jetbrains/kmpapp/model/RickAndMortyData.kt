package com.jetbrains.kmpapp.model

import kotlinx.serialization.Serializable

@Serializable
data class RickAndMortyData(
    val info: Info? = null,
    val results: List<Result>? = null
)