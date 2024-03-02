package com.jetbrains.kmpapp

import android.app.Application
import com.jetbrains.kmpapp.data.factory.ApplicationComponent
import com.jetbrains.kmpapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class RickAndMorty :  Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@RickAndMorty)
            ApplicationComponent.init()
        }
        initLogger()


    }
}
