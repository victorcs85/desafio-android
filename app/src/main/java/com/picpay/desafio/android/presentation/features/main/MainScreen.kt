package com.picpay.desafio.android.presentation.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.picpay.desafio.android.presentation.navigation.AppNavigation
import com.picpay.desafio.android.presentation.theme.BLACK_APP_COLOR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BLACK_APP_COLOR,
                )
            )
        },
        content = { innerPadding ->
            AppNavigation(innerPadding, navController)
        }
    )
}