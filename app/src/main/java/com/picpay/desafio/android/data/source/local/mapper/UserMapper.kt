package com.picpay.desafio.android.data.source.local.mapper

import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User

class UserMapper : DomainMapper<UserEntity, User> {

    override fun toDomain(from: UserEntity) = with(from) {
        User(id.toString(), name, username.toInt(), img)
    }
}

fun UserResponse.toEntity() =
    UserEntity(id ?: 0, name.orEmpty(), username.orEmpty(), img.orEmpty())