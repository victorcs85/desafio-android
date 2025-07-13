package com.picpay.desafio.android.domain.usecases

import com.picpay.desafio.android.domain.repository.UsersResponse

interface IGetUsersUseCase {
    suspend operator fun invoke(): UsersResponse
}