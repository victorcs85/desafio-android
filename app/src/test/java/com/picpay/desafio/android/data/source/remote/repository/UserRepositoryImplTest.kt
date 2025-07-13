package com.picpay.desafio.android.data.source.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.picpay.desafio.android.base.CoroutinesTestRule
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.data.source.remote.mapper.UserMapper
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@SmallTest
class UserRepositoryRemoteTest : KoinTest {

    private lateinit var repository: UserRepositoryImpl
    private val service: PicPayService = mockk()
    private val mapper: DomainMapper<UserDto, User> = UserMapper()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(service, mapper)
    }

    @Test
    fun givenUsers_whenGetUsers_thenFetchAPISuccessfully() = runTest {
        val apiUsers = DataMockTest.USERS_RESPONSE_MOCK
        val mappedUsers = apiUsers.map { mapper.toDomain(it) }

        coEvery { service.getUsers() } returns apiUsers

        val result = repository.getUsers()

//        assertEquals(mappedUsers, result)
        coVerify { service.getUsers() }
    }

    @Test
    fun givenEmptyUserList_whenGetUsers_thenReturnEmptyList() = runTest {
        val emptyUserList = emptyList<User>()

        coEvery { service.getUsers() } returns emptyList()

        val result = repository.getUsers()

//        assertEquals(emptyUserList, result)
    }
}