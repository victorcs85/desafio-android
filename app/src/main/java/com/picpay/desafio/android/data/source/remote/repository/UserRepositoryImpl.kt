package com.picpay.desafio.android.data.source.remote.repository

import com.picpay.desafio.android.core.extensions.async
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val service: PicPayService,
    private val mapper: DomainMapper<UserResponse, User>
) : UserRepository {

    override suspend fun getUsers(): List<User> = async {
        mapper.toDomain(service.getUsers().distinct())
    }

}