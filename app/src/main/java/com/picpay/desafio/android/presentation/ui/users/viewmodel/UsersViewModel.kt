package com.picpay.desafio.android.presentation.ui.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.core.base.BaseViewModel
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class UsersViewModel(
    private val repository: UserRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun fetchUsers() = launch(
        enableLoading = true,
        block = {
            _users.postValue(repository.getUsers())
        },
        errorBlock = {
            it.printStackTrace()
        }
    )
}