package com.picpay.desafio.android.shared.test

import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.domain.model.ErrorType
import br.com.victorcs.core.domain.model.Response
import br.com.victorcs.core.exceptions.WithoutNetworkException
import com.picpay.desafio.android.data.source.remote.dto.UserDto
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.features.users.UsersScreenState
import java.util.InputMismatchException

private const val ID_USER = 1001
private const val NAME_USER = "Eduardo Santos"
private const val IMG_USER = "https://randomuser.me/api/port/1.jpg"
private const val USERNAME = "@eduardo.santos"
const val GENERIC_ERROR = "Ocorreu um erro ao buscar os dados!"
const val NETWORK_ERROR = "Sem conexão. Verifique e tente novamente."

object DataMockTest {

    val usersMock = listOf(
        User(
            id = ID_USER,
            name = NAME_USER,
            img = IMG_USER,
            username = USERNAME
        )
    )

    val usersResponseMock = Response.Success(
        listOf(
            User(
                id = ID_USER,
                name = NAME_USER,
                img = IMG_USER,
                username = USERNAME
            )
        )
    )

    val usersDtoMock = listOf(
        UserDto(
            id = ID_USER,
            name = NAME_USER,
            img = IMG_USER,
            username = USERNAME
        )
    )

    val userScreenStateMock = UsersScreenState(
        isLoading = false,
        users = usersMock,
        errorMessage = null
    )

    val genericErrorMock = InputMismatchException(GENERIC_MESSAGE_ERROR)
    val errorNetworkMock = WithoutNetworkException()
    val genericResponseErrorMock = Response.Error(
        errorMessage = GENERIC_ERROR,
        errorType = ErrorType.GENERIC_ERROR
    )

}