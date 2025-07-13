package com.picpay.desafio.android.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.picpay.desafio.android.R

const val ERROR_MESSAGE_VIEW = "error_message_view"

@Composable
fun ShowErrorMessage(
    errorMessage: String,
    buttonAction: () -> Unit,
    buttonLabel: String?,
    modifier: Modifier?,
) {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = errorMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(ERROR_MESSAGE_VIEW)
                    .padding(16.dp).semantics {
                        liveRegion = LiveRegionMode.Assertive
                    },
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
            buttonLabel?.let {
                val contentButtonDescription = stringResource(R.string.semantic_button, buttonLabel)
                ActionButton(
                    modifier?.semantics { contentDescription = contentButtonDescription },
                    buttonAction,
                    it
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowErrorMessagePreview() {
    ShowErrorMessage("No data available", modifier = Modifier.fillMaxWidth(), buttonLabel = "Retry", buttonAction = {})
}
