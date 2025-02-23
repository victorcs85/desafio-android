package com.picpay.desafio.android.core.interceptor

import com.picpay.desafio.android.core.constants.NETWORK_ERROR
import com.picpay.desafio.android.core.services.WifiService
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val wifiService: WifiService) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (wifiService.isOnline().not()) {
            throw IOException(NETWORK_ERROR)
        } else {
            return chain.proceed(chain.request())
        }
    }
}