package com.picpay.desafio.android.data.source.remote.repository

import androidx.test.filters.SmallTest
import com.picpay.desafio.android.base.CoroutineTestRule
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.shared.test.DataMockTest
import com.picpay.desafio.android.shared.test.GENERIC_ERROR
import com.picpay.desafio.android.shared.test.NETWORK_ERROR
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class UserRepositoryRemoteTest {

    private val remoteDataService = mockk<PicPayService>(relaxed = true)
    private val mapper = mockk<DomainMapper<UserDto, User>>(relaxed = true)

    private lateinit var repository: IUserRepository

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        repository = UserRepositoryImpl(remoteDataService, mapper)
    }

    @Test
    fun givenUsers_whenGetUsers_thenFetchAPISuccessfully() = runTest {
        val apiUsers = DataMockTest.usersDtoMock
        val mappedUsers = DataMockTest.usersMock

        coEvery { remoteDataService.getUsers() } returns apiUsers
        every { mapper.toDomain(apiUsers) } returns mappedUsers

        val result = repository.getUsers()

        assert(result is Response.Success && result.data == mappedUsers)
    }

    @Test
    fun givenUsers_whenFetchData_thenReturnError() = runTest {
        val apiUsers = DataMockTest.usersDtoMock
        val mappedUsers = DataMockTest.usersMock

        coEvery {
            remoteDataService.getUsers()
        } throws DataMockTest.genericErrorMock
        every { mapper.toDomain(apiUsers) } returns mappedUsers

        val result = repository.getUsers()

        assert(result is Response.Error && result.errorMessage.contains(GENERIC_ERROR))
    }

    @Test
    fun givenWithoutNetwork_whenFetchUsersData_thenReturnError() = runTest {
        val apiUsers = DataMockTest.usersDtoMock
        val mappedUsers = DataMockTest.usersMock

        coEvery {
            remoteDataService.getUsers()
        } throws DataMockTest.errorNetworkMock
        every { mapper.toDomain(apiUsers) } returns mappedUsers

        val result = repository.getUsers()

        assert(result is Response.Error && result.errorMessage.contains(NETWORK_ERROR))
    }
}