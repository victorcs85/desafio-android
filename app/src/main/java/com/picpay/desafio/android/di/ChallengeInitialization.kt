package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.constants.API_URL
import com.picpay.desafio.android.data.mapper.UserMapper
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.RetrofitConfig
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.ui.users.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.scope.Scope
import org.koin.dsl.module

class ChallengeInitialization: ModuleInitialization() {
    override fun init(): List<Module> = listOf(
        module {
            single { retrofitConfig(PicPayService::class.java) }

            //region Repositories
            single<UserRepository> { UserRepositoryImpl(get(), get()) }
            //endregion

            //region Mappers
            single { UserMapper() }
            //endregion

            //region ViewModels
            viewModel { UsersViewModel(get()) }
            //endregion
        }
    )

    private fun <T> Scope.retrofitConfig(service: Class<T>) = RetrofitConfig.create(
        service,
        API_URL
    )
}