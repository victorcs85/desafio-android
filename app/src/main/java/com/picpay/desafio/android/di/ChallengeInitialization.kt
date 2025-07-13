package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.constants.API_URL
import com.picpay.desafio.android.core.interceptor.ConnectivityInterceptor
import com.picpay.desafio.android.core.services.WifiService
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.RetrofitConfig
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.domain.usecases.GetUsersUseCaseImpl
import com.picpay.desafio.android.domain.usecases.IGetUsersUseCase
import com.picpay.desafio.android.presentation.features.users.UsersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module
import com.picpay.desafio.android.data.source.remote.mapper.UserMapper as RemoteUserMapper
import com.picpay.desafio.android.data.source.remote.repository.UserRepositoryImpl as RemoteUserRepositoryImpl

private const val REMOTE_MAPPER = "remote mapper"

class ChallengeInitialization : ModuleInitialization() {

    //region Data Sources
    private val dataSourceModule = module {
        single { retrofitConfig(PicPayService::class.java) }
    }
    //endregion

    //region Repositories
    private val repositoriesModule = module {
        single<IUserRepository> {
            RemoteUserRepositoryImpl(
                service = get(),
                mapper = get()
            )
        }
    }
    //endregion

    //region Use Cases
    private val useCaseModule = module {
        single<IGetUsersUseCase> {
            GetUsersUseCaseImpl(repository = get())
        }
    }
    //endregion

    //region Mappers
    private val mappersModule = module {
        single<DomainMapper<UserDto, User>> { RemoteUserMapper() }
    }
    //endregion

    //region ViewModels
    private val viewModelModule = module {
        viewModel {
            UsersViewModel(
                useCase = get()
            )
        }
    }
    //endregion

    override fun init(): List<Module> = listOf(
        dataSourceModule,
        repositoriesModule,
        mappersModule,
        viewModelModule,
        serviceModule,
        interceptorModule,
        useCaseModule
    )

    //region Network
    private fun <T> Scope.retrofitConfig(service: Class<T>) = RetrofitConfig.create(
        service,
        baseUrl = API_URL,
        wifiService = get(),
        context = androidContext()
    )

    private val serviceModule = module {
        single { WifiService(androidContext()) }
    }

    private val interceptorModule = module {
        single { ConnectivityInterceptor(get()) }
    }
    //endregion
}