package com.picpay.desafio.android.data.source.remote

import com.picpay.desafio.android.data.source.remote.response.UserResponse
import retrofit2.http.GET


interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}