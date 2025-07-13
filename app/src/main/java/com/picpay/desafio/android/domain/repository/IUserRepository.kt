package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User

typealias UsersResponse = Response<List<User>>

interface IUserRepository {
    suspend fun getUsers(): UsersResponse
}