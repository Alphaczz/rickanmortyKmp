package com.jetbrains.kmpapp.data.factory

import RickAndMortyPreferences
import RickAndMortyPreferencesImpl
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.plus

interface CoreComponent : CoroutinesComponent {
    val appPreferences: RickAndMortyPreferences
}

internal class CoreComponentImpl internal constructor() : CoreComponent,
    CoroutinesComponent by CoroutinesComponentImpl.create() {

    private val dataStore: DataStore<Preferences> = dataStorePreferences(
        corruptionHandler = null,
        coroutineScope = applicationScope + Dispatchers.IO,
        migrations = emptyList()
    )

    override val appPreferences : RickAndMortyPreferences = RickAndMortyPreferencesImpl(dataStore)
}