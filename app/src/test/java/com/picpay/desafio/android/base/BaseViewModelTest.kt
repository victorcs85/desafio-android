package com.picpay.desafio.android.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {

    @get:Rule
    private var coroutineRule = CoroutineTestRule()

    protected fun runBlockingTest(block: suspend TestScope.() -> Unit) =
        coroutineRule.runBlockingTest(block)

    protected val testDispatcherProvider = coroutineRule.testDispatcherProvider

    protected val testDispatcher = coroutineRule.testDispatcher
}
