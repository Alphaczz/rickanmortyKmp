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
        initLogger()




    }
}
@kotlinx.serialization.Serializable
data class ObjectData(
    val name: String,
    val data: ObjectDetails
)

@Serializable
data class ObjectDetails(
    val year: Int,
    val price: Double,
    val cpuModel: String,
    val hardDiskSize: String
)
