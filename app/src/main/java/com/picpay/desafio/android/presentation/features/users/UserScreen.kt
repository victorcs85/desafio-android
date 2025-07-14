package com.picpay.desafio.android.presentation.features.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.theme.AppTheme
import com.picpay.desafio.android.presentation.theme.BLACK_APP_COLOR
import com.picpay.desafio.android.presentation.views.EmptyInfoListView
import com.picpay.desafio.android.presentation.views.LoadingView
import com.picpay.desafio.android.presentation.views.ShowErrorMessage

private const val TAG_TEST_CONTACT_TITLE = "ContactTitle"

@Composable
fun UsersScreen(
    state: UsersScreenState,
    execute: (UsersIntent) -> Unit
) {
    val semanticUsers = stringResource(R.string.semantic_users)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(BLACK_APP_COLOR)
                .fillMaxSize()
                .padding(top = 24.dp)
                .semantics() { contentDescription = semanticUsers }
        ) {
            Text(
                text = stringResource(R.string.contacts_title),
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .testTag(TAG_TEST_CONTACT_TITLE),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            UsersScreenContent(state, execute)
        }
    }
}

@Composable
fun UsersScreenContent(
    state: UsersScreenState,
    execute: (UsersIntent) -> Unit
) {

    val listState = rememberLazyListState()

    when {
        state.errorMessage != null -> ShowErrorMessage(
            errorMessage = state.errorMessage,
            buttonLabel = stringResource(R.string.reload),
            buttonAction = {
                execute(UsersIntent.FetchUsers)
            },
            modifier = null,
        )

        state.isLoading -> LoadingView()
        state.users?.isEmpty() == true || state.users == null -> EmptyInfoListView(
            buttonLabel = stringResource(R.string.reload),
            buttonAction = {
                execute(UsersIntent.FetchUsers)
            },
            modifier = null,
        )

        else -> UserList(
            users = state.users,
            listState = listState
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview_Loading() {
    val state = UsersScreenState(isLoading = true)
    AppTheme {
        UsersScreen(state = state, execute = {})
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview_Error() {
    val state = UsersScreenState(
        errorMessage = stringResource(R.string.error)
    )
    AppTheme {
        UsersScreen(state = state, execute = {})
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview_Empty() {
    val state = UsersScreenState(users = emptyList())
    AppTheme {
        UsersScreen(state = state, execute = {})
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenPreview_Success() {
    val users = listOf(
        User(
            "https://randomuser.me/api/portraits/men/1.jpg",
            "Eduardo Santos",
            1,
            "eduardo.santos"
        ),
        User(
            "https://randomuser.me/api/portraits/women/2.jpg",
            "Marina Coelho",
            2,
            "marina.coelho"
        ),
        User(
            "https://randomuser.me/api/portraits/women/3.jpg",
            "Márcia da Silva",
            3,
            "marcia.silva"
        ),
        User("https://randomuser.me/api/portraits/men/4.jpg", "Fabrício Val", 4, "fabricio.val"),
        User(
            "https://randomuser.me/api/portraits/women/5.jpg",
            "Júlia Magalhães",
            5,
            "julia.magalhaes"
        )
    )
    val state = UsersScreenState(users = users)
    AppTheme {
        UsersScreen(state = state, execute = {})
    }
}
