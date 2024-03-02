package com.jetbrains.kmpapp.di


import com.jetbrains.kmpapp.data.repository.IRepository
import com.jetbrains.kmpapp.data.repository.RepositoryImpl
import com.jetbrains.kmpapp.data.repository.localRepo.ILocalData
import com.jetbrains.kmpapp.data.repository.localRepo.localDataImpl
import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.DatabaseDriverFactory
import com.jetbrains.kmpapp.data.repository.localRepo.sqldelight.SharedDatabase
import com.jetbrains.kmpapp.data.repository.remoteRepo.IRemoteData
import com.jetbrains.kmpapp.data.repository.remoteRepo.RemoteDataimpl
import com.jetbrains.kmpapp.screens.detail.DetailScreenModel
import com.jetbrains.kmpapp.screens.list.ListScreenModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule,
            screenModelsModule,
            apiServiceModule,
            appModule,
            sqlDelightModule,
            repositoryModule,
            platformModule()
        )
    }

val dataModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }
    single<IRemoteData> { RemoteDataimpl(get()) }
   // single<RickAndMortyApi> { RickAndMortyApiClient(get()) }
   // single<RickAndMortyStorage> { InMemoryRickAndMortyStorage() }
//    single {
//        rickAndMortyRepository(get(), get()).apply {
//            initialize()
//        }
//    }

}

val apiServiceModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        RemoteDataimpl(HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }) as IRemoteData
    }
}
val screenModelsModule = module {
    factoryOf(::ListScreenModel)
    factoryOf(::DetailScreenModel)
}
val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}
val appModule = module {

    single {
        SharedDatabase(
            databaseDriverFactory = get()
        )
    }




}

val repositoryModule = module {
    //single<IRepository> { RepositoryImp(get(), get()) }
    single<IRemoteData> { RemoteDataimpl(get()) }
    single <ILocalData>{ localDataImpl(get()) }
    single <IRepository> { RepositoryImpl(get(),get()) }
   // single<IRemoteData> {  }


}
//val androidModule = module {
//    single<PreferenceData> {  }
//}
//fun initKoin() {
//    startKoin {
//        modules(
//            dataModule,
//            screenModelsModule,
//            apiServiceModule,
//            appModule,
//            sqlDelightModule,
//            repositoryModule,
//            platformModule()
//        )
//    }
//}

fun initKoin() = initKoin {}