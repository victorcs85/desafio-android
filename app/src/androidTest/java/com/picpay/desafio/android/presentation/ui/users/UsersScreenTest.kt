package com.picpay.desafio.android.presentation.ui.users

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.filters.MediumTest
import com.picpay.desafio.android.domain.usecases.IFetchUsersUseCase
import com.picpay.desafio.android.presentation.MainActivity
import com.picpay.desafio.android.presentation.features.users.TAG_TEST_USER_IMAGE
import com.picpay.desafio.android.presentation.features.users.TAG_TEST_USER_ITEM
import com.picpay.desafio.android.presentation.features.users.TAG_TEST_USER_LIST
import com.picpay.desafio.android.presentation.features.users.TAG_TEST_USER_NAME
import com.picpay.desafio.android.presentation.features.users.TAG_TEST_USER_USERNAME
import com.picpay.desafio.android.presentation.features.users.UsersScreen
import com.picpay.desafio.android.presentation.features.users.UsersScreenState
import com.picpay.desafio.android.presentation.features.users.UsersViewModel
import com.picpay.desafio.android.presentation.views.EMPTY_INFO_LIST_VIEW
import com.picpay.desafio.android.presentation.views.ERROR_MESSAGE_VIEW
import com.picpay.desafio.android.presentation.views.LOADING_VIEW_TEST_TAG
import com.picpay.desafio.android.shared.test.EMPTY_DATA_ERROR
import com.picpay.desafio.android.shared.test.GENERIC_ERROR
import com.picpay.desafio.android.shared.test.ID_USER
import com.picpay.desafio.android.shared.test.IMG_USER
import com.picpay.desafio.android.shared.test.NAME_USER
import com.picpay.desafio.android.shared.test.PresentationDataMock
import com.picpay.desafio.android.shared.test.USERNAME
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
class UsersScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: UsersViewModel

    private val useCase = mockk<IFetchUsersUseCase>()

    @Before
    fun setUp() {
        coEvery { useCase.invoke() } returns PresentationDataMock.usersMock
    }

    private fun launchScreen() {
        viewModel = UsersViewModel(useCase)
        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                val state = viewModel.screenState.collectAsStateWithLifecycle().value
                UsersScreen(
                    state = state,
                    execute = viewModel::execute
                )
            }
        }
    }

    @Test
    fun givenScreen_whenLoadedData_thenSuccessfullyData() {
        launchScreen()
        composeTestRule.waitUntil(
            timeoutMillis = 5_000,
            condition = {
                composeTestRule
                    .onAllNodesWithTag("${TAG_TEST_USER_NAME}${NAME_USER}")
                    .fetchSemanticsNodes().isNotEmpty()
            }
        )
        composeTestRule.run {
            onNodeWithTag("${TAG_TEST_USER_ITEM}${ID_USER}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_NAME}${NAME_USER}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_USERNAME}${USERNAME}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_IMAGE}${IMG_USER}").isDisplayed()
            onNodeWithText(TAG_TEST_USER_LIST).assertIsDisplayed()
        }
    }

    @Test
    fun givenEmptyData_whenLoadedData_thenShowError() {
        launchScreen()
        coEvery { useCase.invoke() } returns PresentationDataMock.usersEmptyMock

        composeTestRule.run {
            onNodeWithTag("${TAG_TEST_USER_ITEM}${ID_USER}").isNotDisplayed()
            onNodeWithTag("${TAG_TEST_USER_NAME}${NAME_USER}").isNotDisplayed()
            onNodeWithTag("${TAG_TEST_USER_USERNAME}${IMG_USER}").isNotDisplayed()
            onNodeWithTag(EMPTY_INFO_LIST_VIEW).isDisplayed()
            onNodeWithText(EMPTY_DATA_ERROR).assertIsDisplayed()
        }
    }

    @Test
    fun givenError_whenLoadedData_thenShowError() {
        launchScreen()
        coEvery { useCase.invoke() } returns PresentationDataMock.genericResponseErrorMock

        composeTestRule.run {
            onNodeWithTag("${TAG_TEST_USER_ITEM}${ID_USER}").isNotDisplayed()
            onNodeWithTag("${TAG_TEST_USER_NAME}${NAME_USER}").isNotDisplayed()
            onNodeWithTag("${TAG_TEST_USER_USERNAME}${IMG_USER}").isNotDisplayed()
            onNodeWithTag(ERROR_MESSAGE_VIEW).isDisplayed()
            onNodeWithText(GENERIC_ERROR).assertIsDisplayed()
        }
    }
    @Test
    fun givenLoadingState_whenScreenComposed_thenShowLoadingView() {
        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                UsersScreen(
                    state = UsersScreenState(isLoading = true),
                    execute = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(LOADING_VIEW_TEST_TAG).assertIsDisplayed()
    }
}
