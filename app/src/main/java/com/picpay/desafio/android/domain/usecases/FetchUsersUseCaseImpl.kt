package com.picpay.desafio.android.domain.usecases

import br.com.victorcs.core.domain.model.Response
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.domain.repository.UsersResponse

class FetchUsersUseCaseImpl(private val repository: IUserRepository) : IFetchUsersUseCase {

    override suspend fun invoke(): UsersResponse {
        return when (val result = repository.getUsers()) {
            is Response.Success -> Response.Success(result.data.map { user ->
                user.copy(username = "@${user.username.trim().lowercase()}")
            }.distinct())

            is Response.Error -> result
        }
    }
}