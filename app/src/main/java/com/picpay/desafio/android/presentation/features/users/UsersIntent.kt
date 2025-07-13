package com.picpay.desafio.android.presentation.features.users

sealed class UsersIntent {
    object FetchUsers : UsersIntent()
}