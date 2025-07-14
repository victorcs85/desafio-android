package com.picpay.desafio.android.presentation.ui.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.picpay.desafio.android.base.BaseViewModelTest
import com.picpay.desafio.android.base.CoroutineTestRule
import com.picpay.desafio.android.domain.usecases.IFetchUsersUseCase
import com.picpay.desafio.android.presentation.features.users.UsersIntent
import com.picpay.desafio.android.presentation.features.users.UsersViewModel
import com.picpay.desafio.android.shared.test.DataMockTest
import com.picpay.desafio.android.shared.test.GENERIC_ERROR
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class UsersViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val useCase = mockk<IFetchUsersUseCase>(relaxed = true)

    private lateinit var viewModel: UsersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UsersViewModel(useCase = useCase, dispatchers = testDispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun givenValidFlow_whenFetchUsersData_thenReturnDataSuccessfully() =
        runTest {

            val responseMock = DataMockTest.usersResponseMock
            val expectedState = DataMockTest.userScreenStateMock

            coEvery {
                useCase.invoke()
            } returns responseMock

            viewModel.screenState.test {
                viewModel.execute(UsersIntent.FetchUsers)

                awaitItem()

                val finalState = awaitItem()

                assertEquals(
                    expectedState.users,
                    finalState.users
                )
                assertNull(finalState.errorMessage)
                assertFalse(finalState.isLoading)

                cancelAndIgnoreRemainingEvents()
            }

            coEvery { useCase.invoke() }
        }

    @Test
    fun givenInvalidFlow_whenFetchUsersData_thenReturnError() =
        runTest {

            val responseMock = DataMockTest.genericResponseErrorMock

            coEvery {
                useCase.invoke()
            } returns responseMock



            viewModel.screenState.test {
                viewModel.execute(UsersIntent.FetchUsers)

                awaitItem()

                val finalState = awaitItem()
                assertEquals(GENERIC_ERROR, finalState.errorMessage)
                assertTrue(finalState.users == null)
                assertFalse(finalState.isLoading)

                cancelAndIgnoreRemainingEvents()
            }

            coEvery { useCase.invoke() }
        }
}