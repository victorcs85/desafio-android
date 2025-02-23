package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.core.constants.API_URL
import com.picpay.desafio.android.core.constants.LOCAL_SOURCE
import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.core.db.ChallengeAppDatabase
import com.picpay.desafio.android.core.interceptor.ConnectivityInterceptor
import com.picpay.desafio.android.core.services.WifiService
import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.RetrofitConfig
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.ui.users.viewmodel.UsersViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.picpay.desafio.android.data.source.local.mapper.UserMapper as LocalUserMapper
import com.picpay.desafio.android.data.source.local.repository.UserRepositoryImpl as LocalUserRepositoryImpl
import com.picpay.desafio.android.data.source.remote.mapper.UserMapper as RemoteUserMapper
import com.picpay.desafio.android.data.source.remote.repository.UserRepositoryImpl as RemoteUserRepositoryImpl

private const val CHALLENGE_APP_DATABASE = "challenge_app_database"
private const val LOCAL_MAPPER = "local mapper"
private const val REMOTE_MAPPER = "remote mapper"

class ChallengeInitialization : ModuleInitialization() {

    //region Data Sources
    private val dataSourceModule = module {
        single { retrofitConfig(PicPayService::class.java) }
        single {
            Room.databaseBuilder(
                get(),
                ChallengeAppDatabase::class.java,
                CHALLENGE_APP_DATABASE
            ).build()
        }
        single { get<ChallengeAppDatabase>().userDao() }
    }
    //endregion

    //region Repositories
    private val repositoriesModule = module {
        single<UserRepository>(named(REMOTE_SOURCE)) {
            RemoteUserRepositoryImpl(
                service = get(),
                mapper = get(named(REMOTE_MAPPER))
            )
        }

        single<UserRepository>(named(LOCAL_SOURCE)) {
            LocalUserRepositoryImpl(
                remoteMapper = get(named(REMOTE_MAPPER)),
                localMapper = get(named(LOCAL_MAPPER)),
                service = get(),
                userDao = get()
            )
        }
    }
    //endregion

    //region Mappers
    private val mappersModule = module {
        single<DomainMapper<UserEntity, User>>(named(LOCAL_MAPPER)) { LocalUserMapper() }
        single<DomainMapper<UserResponse, User>>(named(REMOTE_MAPPER)) { RemoteUserMapper() }
    }
    //endregion

    //region ViewModels
    private val viewModelModule = module {
        viewModel {
            UsersViewModel(
                remoteRepository = get(named(REMOTE_SOURCE)),
                localRepository = get(named(LOCAL_SOURCE))
            )
        }
    }
    //endregion

    override fun init(): List<Module> = listOf(
        dataSourceModule,
        repositoriesModule,
        mappersModule,
        viewModelModule,
        networkModule,
        serviceModule,
        interceptorModule
    )

    //region Network
    private fun <T> Scope.retrofitConfig(service: Class<T>) = RetrofitConfig.create(
        service,
        API_URL,
        get()
    )

    private val networkModule = module {
        single { OkHttpClient.Builder().addInterceptor(get<Interceptor>()).build() }
        single {
            Retrofit.Builder().client(get()).addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    private val serviceModule = module {
        single { WifiService(androidContext()) }
    }

    private val interceptorModule = module {
        single { ConnectivityInterceptor(get()) }
    }
    //endregion
}