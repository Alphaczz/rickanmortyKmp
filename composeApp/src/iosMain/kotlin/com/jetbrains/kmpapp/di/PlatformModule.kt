package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.factory.DatabaseDriverFactory
import com.jetbrains.kmpapp.utils.ApiService
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { ApiService() }

}
