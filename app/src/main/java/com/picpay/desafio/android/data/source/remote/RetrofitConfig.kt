package com.picpay.desafio.android.data.source.remote

import android.content.Context
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.core.interceptor.CacheControlInterceptor
import com.picpay.desafio.android.core.interceptor.ConnectivityInterceptor
import com.picpay.desafio.android.core.services.WifiService
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val HTTP_CACHE = "http-cache"
private const val HUNDRED = 100L
private const val ONE_HUNDRED_AND_TWENTY = 120L
private const val CACHE_MAX_SIZE = 10L * 1024L * 1024L

object RetrofitConfig {

    fun <T> create(service: Class<T>, baseUrl: String, wifiService: WifiService, context: Context): T {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(CacheControlInterceptor(wifiService))
            .addInterceptor(ConnectivityInterceptor(wifiService))
            .addInterceptor(getHttpLogging())
            .connectTimeout(HUNDRED, TimeUnit.SECONDS)
            .readTimeout(HUNDRED, TimeUnit.SECONDS)
            .writeTimeout(ONE_HUNDRED_AND_TWENTY, TimeUnit.SECONDS)
            .cache(
                Cache(
                    File(context.cacheDir, HTTP_CACHE),
                    CACHE_MAX_SIZE,
                ),
            )
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(MoshiBuilder.create()))
            .build()
            .create(service)
    }

    private fun getHttpLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
}