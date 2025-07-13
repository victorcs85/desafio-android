package com.picpay.desafio.android.presentation.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.theme.AppTheme
import com.picpay.desafio.android.presentation.theme.GREEN_COLOR

const val LOADING_VIEW_TEST_TAG = "loading_view"

@Composable
fun LoadingView() {
    val loadingContentDescription = stringResource(R.string.semantic_loading)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = GREEN_COLOR,
            modifier = Modifier
                .semantics {
                    contentDescription = loadingContentDescription
                }
                .testTag(LOADING_VIEW_TEST_TAG),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingViewPreview() {
    AppTheme {
        LoadingView()
    }
}