package com.picpay.desafio.android.data.source.remote.mapper

import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User

private const val ZERO = 0

class UserMapper : DomainMapper<UserResponse, User> {

    override fun toDomain(from: UserResponse): User = User(
        img = from.img.orEmpty(),
        name = from.name.orEmpty(),
        id = from.id ?: ZERO,
        username = from.username.orEmpty()
    )

}