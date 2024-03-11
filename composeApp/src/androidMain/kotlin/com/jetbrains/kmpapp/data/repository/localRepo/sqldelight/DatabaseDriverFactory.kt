package com.jetbrains.kmpapp.data.repository.localRepo.sqldelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.RickandMortyDb

actual class DatabaseDriverFactory(private val context: Context) {
    actual  fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RickandMortyDb.Schema, context, "test.db")
    }
}