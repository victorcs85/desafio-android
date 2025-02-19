package com.picpay.desafio.android.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.source.local.entity.UserEntity

private const val DB_VERSION = 1

@Database(entities = [UserEntity::class], version = DB_VERSION, exportSchema = false)
abstract class ChallengeAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}