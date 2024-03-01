package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
}
