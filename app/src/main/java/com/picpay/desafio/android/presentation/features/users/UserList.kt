package com.picpay.desafio.android.presentation.features.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.theme.AppTheme
import com.picpay.desafio.android.presentation.theme.BLACK_APP_COLOR

const val TAG_TEST_USER_LIST = "UserList"

@Composable
fun UserList(
    users: List<User>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
            .testTag(TAG_TEST_USER_LIST)
    ) {
        items(
            count = users.size,
            key = { users[it].id },
            itemContent = { index ->
                val user = users[index]

                UserItem(
                    user = user,
                    onClick = {  },
                    modifier = Modifier.testTag("$TAG_TEST_USER_ITEM${user.id}")
                )

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListPreview() {
    val listState = rememberLazyListState()

    val sampleUsers = listOf(
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
    AppTheme {
        Surface(color = BLACK_APP_COLOR) {
            UserList(sampleUsers, listState)
        }
    }
}