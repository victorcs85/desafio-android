package com.picpay.desafio.android.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatchersProvider : IDispatchersProvider {

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    override val io: CoroutineDispatcher
        get() = testDispatcher

    override val main: CoroutineDispatcher
        get() = testDispatcher

    override val default: CoroutineDispatcher
        get() = testDispatcher
}

interface IDispatchersProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}