package com.picpay.desafio.android.data.source.local.repository

import com.picpay.desafio.android.core.extensions.async
import com.picpay.desafio.android.data.source.local.dao.UserDao
import com.picpay.desafio.android.data.source.local.entity.UserEntity
import com.picpay.desafio.android.data.source.local.mapper.toEntity
import com.picpay.desafio.android.data.source.remote.PicPayService
import com.picpay.desafio.android.data.source.remote.response.UserResponse
import com.picpay.desafio.android.domain.mapper.DomainMapper
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val service: PicPayService,
    private val localMapper: DomainMapper<UserEntity, User>,
    private val remoteMapper: DomainMapper<UserResponse, User>,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUsers(): List<User> = async {
        val cachedUsers = userDao.getUsers().map { localMapper.toDomain(it) }
        if (cachedUsers.isNotEmpty()) {
            return@async cachedUsers
        }

        val apiUsers = service.getUsers()
        userDao.run {
            clearUsers()
            insertUsers(apiUsers.distinct().map { it.toEntity() })
        }
        remoteMapper.toDomain(apiUsers)
    }
}