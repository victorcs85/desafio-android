package com.picpay.desafio.android.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

private const val USERS_TABLE_NAME = "users"

@Entity(tableName = USERS_TABLE_NAME)
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val img: String
)