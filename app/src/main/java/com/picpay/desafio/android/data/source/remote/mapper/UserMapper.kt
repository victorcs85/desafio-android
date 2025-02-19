package com.picpay.desafio.android.data.source.remote.mapper

import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User

class UserMapper : DomainMapper<UserResponse, User> {

    override fun toDomain(from: UserResponse): User = User(
        img = from.img.orEmpty(),
        name = from.name.orEmpty(),
        id = from.id ?: 0,
        username = from.username.orEmpty()
    )

}