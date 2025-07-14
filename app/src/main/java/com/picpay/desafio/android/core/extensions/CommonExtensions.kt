package com.picpay.desafio.android.core.extensions

import com.picpay.desafio.android.core.constants.GENERIC_MESSAGE_ERROR
import com.picpay.desafio.android.core.constants.NETWORK_ERROR
import com.picpay.desafio.android.core.constants.ZERO
import com.picpay.desafio.android.data.source.remote.exceptions.WithoutNetworkException
import com.picpay.desafio.android.domain.model.ErrorType
import com.picpay.desafio.android.domain.model.Response
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Response<T> {
    return try {
        Response.Success(apiCall())
    } catch (e: Exception) {
        Timber.e(e)
        when (e) {
            is WithoutNetworkException, is IOException ->
                Response.Error(e.message ?: NETWORK_ERROR, ErrorType.NETWORK_ERROR)
            else ->
                Response.Error(GENERIC_MESSAGE_ERROR, ErrorType.GENERIC_ERROR)
        }
    }
}

fun Int?.orZero(): Int = this ?: ZERO
