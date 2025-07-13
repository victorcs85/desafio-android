package com.picpay.desafio.android.presentation.navigation

private const val USERS_SCREEN = "users"

sealed class ScreenRouter(val route: String) {
    object Users : ScreenRouter(USERS_SCREEN)
}
