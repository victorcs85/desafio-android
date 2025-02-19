package com.picpay.desafio.android.data.source.local.mapper

import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User

class UserMapper : DomainMapper<UserEntity, User> {

    override fun toDomain(from: UserEntity) = with(from) {
        User(id = id, name = name, username = username, img = img)
    }
}

fun UserResponse.toEntity() =
    UserEntity(
        id = id ?: 0,
        name = name.orEmpty(),
        username = username.orEmpty(),
        img = img.orEmpty()
    )