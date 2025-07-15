package com.picpay.desafio.android.data.source.remote.repository

import br.com.victorcs.core.extensions.safeApiCall
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.IUserRepository
import com.picpay.desafio.android.domain.repository.UsersResponse

class UserRepositoryImpl(
    private val service: PicPayService,
    private val mapper: DomainMapper<UserDto, User>
) : IUserRepository {

    override suspend fun getUsers(): UsersResponse = safeApiCall {
        mapper.toDomain(service.getUsers())
    }

}