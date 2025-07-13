package com.picpay.desafio.android.domain.model

sealed class Response<out T> {
    data class Success<out T>(
        val data: T,
    ) : Response<T>()

    data class Error(
        val errorMessage: String,
        val errorType: ErrorType? = null,
    ) : Response<Nothing>()
}

enum class ErrorType {
    NETWORK_ERROR,
    GENERIC_ERROR
}
