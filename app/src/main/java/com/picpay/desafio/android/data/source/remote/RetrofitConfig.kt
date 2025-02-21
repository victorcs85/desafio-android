package com.picpay.desafio.android.data.source.remote

import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig : KoinComponent {

    fun <T> create(
        service: Class<T>,
        baseUrl: String
    ): T {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                okHttpClient
            )
            .addConverterFactory(
                MoshiConverterFactory.create(MoshiBuilder.create())
            )
            .build()
            .create(service)
    }

}