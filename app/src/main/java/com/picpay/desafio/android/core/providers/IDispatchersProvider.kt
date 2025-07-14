package com.picpay.desafio.android.core.providers

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchersProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}