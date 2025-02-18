package com.picpay.desafio.android.presentation.ui.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.core.base.BaseViewModel
import com.picpay.desafio.android.core.extensions.asFailureResponse
import com.picpay.desafio.android.core.extensions.asSuccessResponse
import com.picpay.desafio.android.domain.model.Response
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

typealias UsersResponse = Response<List<User>>

class UsersViewModel(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<UsersResponse>(Response.Idle)
    val users: LiveData<UsersResponse> = _users

    fun fetchUsers() {
        _users.value = Response.Loading
        launch(
            block = {
                _users.postValue(repository.getUsers().asSuccessResponse())
            },
            errorBlock = {
                it.printStackTrace()
                _users.postValue(it.asFailureResponse())
            }
        )
    }
}