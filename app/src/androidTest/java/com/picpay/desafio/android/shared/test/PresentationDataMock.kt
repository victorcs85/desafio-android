package com.picpay.desafio.android.shared.test

import com.picpay.desafio.android.domain.model.ErrorType
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User

const val ID_USER = 1001
const val NAME_USER = "Eduardo Santos"
const val IMG_USER = "https://randomuser.me/api/port/1.jpg"
const val USERNAME = "@eduardo.santos"
const val EMPTY_DATA_ERROR = "Não foram localizados dados para amostragem."
const val GENERIC_ERROR = "Ocorreu um erro ao buscar os dados!"

object PresentationDataMock {
    val usersMock = Response.Success(
        listOf(
            User(
                id = ID_USER,
                name = NAME_USER,
                img = IMG_USER,
                username = USERNAME
            )
        )
    )

    val usersEmptyMock = Response.Success(emptyList<User>())

    val genericResponseErrorMock = Response.Error(
        errorMessage = GENERIC_ERROR,
        errorType = ErrorType.GENERIC_ERROR
    )
}