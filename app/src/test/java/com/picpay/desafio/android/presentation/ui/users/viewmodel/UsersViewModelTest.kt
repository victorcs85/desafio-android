package com.picpay.desafio.android.presentation.ui.users.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import br.com.mantunes.applembrei.base.CoroutinesTestRule
import com.picpay.desafio.android.base.test
import com.picpay.desafio.android.di.ChallengeInitialization
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.mockito.junit.MockitoJUnitRunner

private const val LOCAL_SOURCE = "local"
private const val REMOTE_SOURCE = "remote"
private const val ERROR = "Ocorreu um erro. Tente novamente."

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
@SmallTest
class UsersViewModelTest : KoinTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val koinRule = KoinTestRule.create {
        printLogger(Level.ERROR)
        allowOverride(true)
        loadKoinModules(
            modules = ChallengeInitialization().init() +
                    module {
                        single(named(REMOTE_SOURCE)) { remoteRepository }
                        single(named(LOCAL_SOURCE)) { localRepository }
                        single {
                            UsersViewModel(
                                remoteRepository = get(named(REMOTE_SOURCE)),
                                localRepository = get(named(LOCAL_SOURCE))
                            )
                        }
                    }
        )
    }

    private val remoteRepository: UserRepository = mockk()
    private val localRepository: UserRepository = mockk()

    private lateinit var usersViewModel: UsersViewModel

    @Before
    fun setUp() {
        usersViewModel = UsersViewModel(remoteRepository, localRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun givenUsersScreen_whenFetchUsers_thenReturnSuccessfully() = runTest {
        val mockUsersResponse = DataMockTest.USERS_MOCK
        coEvery { localRepository.getUsers() } returns mockUsersResponse

        val usersObserver = usersViewModel.users.test()

        usersViewModel.fetchUsers()

        verifySequence {
            usersObserver.run {
                onChanged(Response.Idle)
                onChanged(Response.Loading)
                onChanged(Response.Success(mockUsersResponse))
            }
        }
        confirmVerified(usersObserver)
    }

    @Test
    fun givenUsersScreen_whenFetchUsersWithError_thenReturnError() = runTest {
        coEvery { localRepository.getUsers() } throws IllegalArgumentException(ERROR)

        val usersObserver = usersViewModel.users.test()

        usersViewModel.fetchUsers()

        verifySequence {
            usersObserver.run {
                onChanged(Response.Idle)
                onChanged(Response.Loading)
                onChanged(Response.Failure(ERROR))
            }
        }
        confirmVerified(usersObserver)

        coVerify { localRepository.getUsers() }
    }

    @Test
    fun givenForceRefresh_whenFetchUsers_thenReturnSuccessfully() = runTest {
        val mockUsersResponse = DataMockTest.USERS_MOCK
        coEvery { remoteRepository.getUsers() } returns mockUsersResponse

        val usersObserver = usersViewModel.users.test()

        usersViewModel.fetchUsers(forceRefresh = true)

        verifySequence {
            usersObserver.run {
                onChanged(Response.Idle)
                onChanged(Response.Loading)
                onChanged(Response.Success(mockUsersResponse))
            }
        }
        confirmVerified(usersObserver)
    }
}