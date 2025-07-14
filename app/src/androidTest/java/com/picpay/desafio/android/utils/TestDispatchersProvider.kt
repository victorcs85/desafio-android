package com.picpay.desafio.android.utils

import com.picpay.desafio.android.core.providers.IDispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatchersProvider(private val testDispatcher: TestDispatcher) : IDispatchersProvider {
    override val io: CoroutineDispatcher get() = testDispatcher
    override val main: CoroutineDispatcher get() = testDispatcher
    override val default: CoroutineDispatcher get() = testDispatcher
}
