package com.jetbrains.kmpapp.data.repository.localRepo.sqldelight

import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.RickandMortyDb
import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual  fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RickandMortyDb.Schema, context, "test.db")
    }
}