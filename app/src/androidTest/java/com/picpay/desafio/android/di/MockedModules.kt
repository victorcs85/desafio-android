package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.constants.LOCAL_SOURCE
import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.presentation.ui.users.viewmodel.UsersViewModel
import io.mockk.mockk
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryMockModules = module(override = true) {
    single(named(LOCAL_SOURCE)) { mockk<UserRepository>(relaxed = true) }
    single(named(REMOTE_SOURCE)) { mockk<UserRepository>(relaxed = true) }
}

val viewModelMockModules = module(override = true) {
    viewModel { UsersViewModel(get(named(REMOTE_SOURCE)), get(named(LOCAL_SOURCE)) ) }
}