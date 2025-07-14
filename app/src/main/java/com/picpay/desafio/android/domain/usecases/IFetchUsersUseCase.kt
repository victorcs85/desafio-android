package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.repository.UsersResponse

interface IFetchUsersUseCase {
    suspend operator fun invoke(): UsersResponse
}