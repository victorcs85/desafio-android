package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.presentation.features.users.UsersViewModel
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryMockModules = module {
    single(named(REMOTE_SOURCE)) { mockk<IUserRepository>(relaxed = true) }
}

val viewModelMockModules = module {
    viewModel { UsersViewModel(get(named(REMOTE_SOURCE))) }
}