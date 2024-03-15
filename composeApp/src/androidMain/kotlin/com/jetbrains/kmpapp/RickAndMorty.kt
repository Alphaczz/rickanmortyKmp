package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.data.factory.ApplicationComponent
import com.jetbrains.kmpapp.data.network.KtorHttpClient
import com.jetbrains.kmpapp.di.initKoin
import io.ktor.client.HttpClient
import kotlinx.serialization.Serializable
import org.koin.android.ext.koin.androidContext

class RickAndMorty :  Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@RickAndMorty)
            ApplicationComponent.init()
        }
        //intialized napier
        initLogger()




    }
}
