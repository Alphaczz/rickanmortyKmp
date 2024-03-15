package com.jetbrains.kmpapp.di


import BaseDataSource
import com.jetbrains.kmpapp.data.network.EndPointsApiServiceImpl
import com.jetbrains.kmpapp.data.network.IEndPoint
import com.jetbrains.kmpapp.data.network.KtorHttpClient
import com.jetbrains.kmpapp.domain.repository.IRepository
import com.jetbrains.kmpapp.domain.repository.RepositoryImpl
import com.jetbrains.kmpapp.data.dataSource.localDataSource.ILocalData
import com.jetbrains.kmpapp.dataSourceImpl.localDataSourceImpl.localDataImpl
import com.jetbrains.kmpapp.data.db.SharedDatabase
import com.jetbrains.kmpapp.data.dataSource.remoteDataSource.IRemoteData
import com.jetbrains.kmpapp.dataSourceImpl.remoteDataSourceImpl.RemoteDataImpl
import com.jetbrains.kmpapp.screens.detail.DetailScreenModel
import com.jetbrains.kmpapp.screens.list.ListScreenModel
import com.jetbrains.kmpapp.utils.NetworkUtils
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            dataModule,
            screenModelsModule,
//            apiServiceModule,
            appModule,
            sqlDelightModule,
            repositoryModule,
            platformModule(),
            utilsModule,


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
    single {
        KtorHttpClient(get()) // Inject the HttpClient provided by Koin
    }
    //single<IRemoteData> { RemoteDataimpl(get()) }
    single <IEndPoint> {
        EndPointsApiServiceImpl(get())
    }

   // single<RickAndMortyApi> { RickAndMortyApiClient(get()) }
   // single<RickAndMortyStorage> { InMemoryRickAndMortyStorage() }
//    single {
//        rickAndMortyRepository(get(), get()).apply {
//            initialize()
//        }
//    }

}

//val apiServiceModule = module {
//    single {
//        val json = Json { ignoreUnknownKeys = true }
//        RemoteDataimpl(HttpClient {
//            install(ContentNegotiation) {
//                json(json, contentType = ContentType.Any)
//            }
//        }) as IRemoteData
//    }
//}
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
val utilsModule= module {
    single { NetworkUtils() }
    single {
        BaseDataSource()
    }
}
val repositoryModule = module {
    //single<IRepository> { RepositoryImp(get(), get()) }
    single<IRemoteData> { RemoteDataImpl(get()) }
    single <ILocalData>{ localDataImpl(get()) }
    single <IRepository> { RepositoryImpl(get(),get(),get(),get()) }
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