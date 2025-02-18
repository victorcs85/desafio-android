package com.picpay.desafio.android.domain.model

sealed class Response<out T> {
    data object Idle : Response<Nothing>()

    data object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : Response<T>()

    data class Failure(
        val errorMessage: String,
    ) : Response<Nothing>()
}
