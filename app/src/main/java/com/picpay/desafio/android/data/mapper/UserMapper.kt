package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.source.remote.entity.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User

class UserMapper : DomainMapper<UserResponse, User> {

    override fun toDomain(from: UserResponse): User = User(
        img = from.img,
        name = from.name,
        id = from.id,
        username = from.username
    )
}