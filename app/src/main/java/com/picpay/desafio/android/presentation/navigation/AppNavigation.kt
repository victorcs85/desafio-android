package com.picpay.desafio.android.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.picpay.desafio.android.presentation.features.users.UsersScreen
import com.picpay.desafio.android.presentation.features.users.UsersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRouter.Users.route,
        modifier = Modifier.padding(innerPadding),
    ) {
        composable(ScreenRouter.Users.route) {
            val viewModel: UsersViewModel = koinViewModel()
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            UsersScreen(state, viewModel::execute)
        }
    }
}
