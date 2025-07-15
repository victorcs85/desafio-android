package br.com.victorcs.core.extensions

import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.constants.NETWORK_ERROR
import br.com.victorcs.core.constants.ZERO
import br.com.victorcs.core.domain.model.ErrorType
import br.com.victorcs.core.domain.model.Response
import br.com.victorcs.core.exceptions.WithoutNetworkException
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
