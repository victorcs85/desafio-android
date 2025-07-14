package com.picpay.desafio.android.domain.usecases

import androidx.test.filters.SmallTest
import com.picpay.desafio.android.base.CoroutineTestRule
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.shared.test.DataMockTest
import com.picpay.desafio.android.shared.test.GENERIC_ERROR
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class FetchUsersUseCaseImplTest {
    private val repository = mockk<IUserRepository>(relaxed = true)

    private lateinit var useCase: IFetchUsersUseCase

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Before
    fun setup() {
        useCase = FetchUsersUseCaseImpl(repository)
    }

    @Test
    fun givenValidFlow_whenFetchUsersData_thenReturnDataSuccessfully() = runTest {
        val responseMock = DataMockTest.usersResponseMock
        val repositoryResponseMock = Response.Success(DataMockTest.usersMock)

        coEvery { useCase.invoke() } returns responseMock
        coEvery { repository.getUsers() } returns repositoryResponseMock

        val result = repository.getUsers()

        assert(result is Response.Success && result.data == responseMock.data)
    }

    @Test
    fun givenInvalidFlow_whenFetchUsersData_thenReturnError() = runTest {
        coEvery { repository.getUsers() } returns DataMockTest.genericResponseErrorMock

        val result = useCase.invoke()

        assert(result is Response.Error && result.errorMessage.contains(GENERIC_ERROR))
    }
}