package com.picpay.desafio.android.data.source.remote.mapper

import br.com.victorcs.core.extensions.orZero
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User


class UserMapper : DomainMapper<UserDto, User> {

    override fun toDomain(from: UserDto): User = User(
        img = from.img.orEmpty(),
        name = from.name.orEmpty(),
        id = from.id.orZero(),
        username = from.username.orEmpty()
    )

}