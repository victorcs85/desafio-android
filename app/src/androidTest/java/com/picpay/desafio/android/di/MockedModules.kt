package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.constants.LOCAL_SOURCE
import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.domain.repository.UserRepository
import io.mockk.mockk
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryMockModules = module(override = true) {
    single(named(LOCAL_SOURCE)) { mockk<UserRepository>(relaxed = true) }
    single(named(REMOTE_SOURCE)) { mockk<UserRepository>(relaxed = true) }
}