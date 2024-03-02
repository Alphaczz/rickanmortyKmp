package com.jetbrains.kmpapp.data.repository.localRepo.sqldelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory  actual constructor(){
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RickandMortyDb.Schema, "test.db")
    }
}