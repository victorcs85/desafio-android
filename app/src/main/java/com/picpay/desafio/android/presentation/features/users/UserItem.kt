package com.picpay.desafio.android.presentation.features.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.theme.AppTheme
import com.picpay.desafio.android.presentation.theme.BLACK_APP_COLOR
import com.picpay.desafio.android.presentation.theme.GREEN_COLOR

@Composable
fun UserItem(user: User, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 16.dp)
            .fillMaxWidth()
            .semantics() {
                contentDescription = "Contato de ${user.name}"
            }
            .testTag("UserItem_${user.id}")
    ) {
        UserImage(user)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "@${user.username}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                modifier = Modifier.testTag("UserUsername_${user.id}")
            )
            Text(
                text = user.name,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                modifier = Modifier.testTag("UserName_${user.id}")
            )
        }
    }
}

@Composable
private fun UserImage(user: User) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(user.img)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .testTag("UserImage_${user.id}"),
        contentAlignment = Alignment.Center
    ) {
        if (painter.state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = GREEN_COLOR
            )
        }

        Image(
            painter = painter,
            contentDescription = "Foto de perfil de ${user.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    val user = User(
        img = "https://randomuser.me/api/portraits/men/1.jpg",
        name = "Eduardo Santos",
        id = 1,
        username = "eduardo.santos"
    )
    AppTheme {
        Surface(color = BLACK_APP_COLOR) {
            UserItem(user, {})
        }
    }
}
