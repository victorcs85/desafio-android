package com.picpay.desafio.android.data.source.local.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.picpay.desafio.android.base.CoroutinesTestRule
import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.shared.test.DataMockTest
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import com.picpay.desafio.android.data.source.local.mapper.UserMapper as UserMapperLocal
import com.picpay.desafio.android.data.source.remote.mapper.UserMapper as UserMapperRemote

private const val ZERO = 0

@ExperimentalCoroutinesApi
@SmallTest
class UserRepositoryLocalTest : KoinTest {

    private lateinit var repository: UserRepositoryImpl
    private val service: PicPayService = mockk()
    private val userDao: UserDao = mockk(relaxed = true)
    private val localMapper: DomainMapper<UserEntity, User> = UserMapperLocal()
    private val remoteMapper: DomainMapper<UserResponse, User> = UserMapperRemote()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(service, localMapper, remoteMapper, userDao)
    }

    @Test
    fun givenCachedUsers_whenGetUsers_thenReturnCachedUsers() = runTest {
        val usersEntity = DataMockTest.USERS_ENTITY_MOCK
        val users = usersEntity.map { localMapper.toDomain(it) }

        coEvery { userDao.getUsers() } returns usersEntity

        val result = repository.getUsers()

        assertEquals(users, result)
        coVerify(exactly = ZERO) { service.getUsers() }
    }

    @Test
    fun givenNoCachedUsers_whenGetUsers_thenFetchDataFromAPIAndCacheIt() = runTest {
        val apiUsers = DataMockTest.USERS_RESPONSE_MOCK
        val mappedUsers = apiUsers.map { remoteMapper.toDomain(it) }

        coEvery { userDao.getUsers() } returns emptyList()
        coEvery { service.getUsers() } returns apiUsers
        coJustRun { userDao.clearUsers() }
        coJustRun { userDao.insertUsers(any()) }

        val result = repository.getUsers()

        assertEquals(mappedUsers, result)
        coVerify { service.getUsers() }
        coVerify { userDao.insertUsers(any()) }
    }
}
