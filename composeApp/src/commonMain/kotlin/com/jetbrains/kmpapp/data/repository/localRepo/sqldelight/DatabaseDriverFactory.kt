package com.jetbrains.kmpapp.data.repository.localRepo.sqldelight

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory{
     fun createDriver(): SqlDriver
}