package com.picpay.desafio.android.data.source.remote.response

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "img") val img: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "username") val username: String?
)