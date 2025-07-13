package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.domain.repository.UsersResponse

class GetUsersUseCaseImpl(private val repository: IUserRepository): IGetUsersUseCase {

    override suspend fun invoke(): UsersResponse {
        return when (val result = repository.getUsers()) {
            is Response.Success -> Response.Success(result.data.distinct())

            is Response.Error -> result
        }
    }
}