package com.picpay.desafio.android.data.source.remote.dto

import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "img") val img: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "id") val id: Int?,
    @Json(name = "username") val username: String?
)