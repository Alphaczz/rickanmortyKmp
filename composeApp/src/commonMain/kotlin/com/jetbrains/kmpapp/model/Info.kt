package com.jetbrains.kmpapp.model

import kotlinx.serialization.Serializable


@Serializable
data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?=null
)