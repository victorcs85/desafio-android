package com.picpay.desafio.android.shared.test

import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User

private const val ID_USER = 1001
private const val NAME_USER = "Eduardo Santos"
private const val IMG_USER = "https://randomuser.me/api/port/1.jpg"
private const val USERNAME = "@eduardo.santos"

object PresentationDataMock {
    val USERS_MOCK = Response.Success(listOf(
        User(
            id = ID_USER,
            name = NAME_USER,
            img = IMG_USER,
            username = USERNAME
        )
    )
    )
}