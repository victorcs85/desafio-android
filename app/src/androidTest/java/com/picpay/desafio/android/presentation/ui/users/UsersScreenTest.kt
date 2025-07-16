package com.picpay.desafio.android.presentation.ui.users

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.filters.MediumTest
import br.com.victorcs.core.providers.IDispatchersProvider
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
import com.picpay.desafio.android.utils.TestDispatchersProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@MediumTest
class UsersScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val useCase = mockk<IFetchUsersUseCase>()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testDispatchersProvider = TestDispatchersProvider(testDispatcher)

    @Before
    fun setUp() {
        coEvery { useCase.invoke() } returns PresentationDataMock.usersMock
    }

    private fun launchScreen() {
        loadKoinModules(module {
            single { useCase }
            single<IDispatchersProvider> { testDispatchersProvider }
            viewModel { UsersViewModel(get(), get()) }

        })

        composeTestRule.activity.setContent {
            val viewModel = org.koin.androidx.compose.koinViewModel<UsersViewModel>()
            val state = viewModel.screenState.collectAsStateWithLifecycle().value

            UsersScreen(
                state = state,
                execute = viewModel::execute
            )
        }

    }

    @Test
    fun givenScreen_whenLoadedData_thenSuccessfullyData() = runTest(testDispatcher) {
        launchScreen()
        testDispatcher.scheduler.runCurrent()

        composeTestRule.run {
            onNodeWithTag(TAG_TEST_USER_LIST).assertIsDisplayed()
            onNodeWithTag("${TAG_TEST_USER_ITEM}${ID_USER}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_NAME}${NAME_USER}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_USERNAME}${USERNAME}").isDisplayed()
            onNodeWithTag("${TAG_TEST_USER_IMAGE}${IMG_USER}").isDisplayed()
            onNodeWithTag(LOADING_VIEW_TEST_TAG).assertDoesNotExist()
        }

    }

    @Test
    fun givenEmptyData_whenLoadedData_thenShowError() = runTest(testDispatcher) {
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
    fun givenError_whenLoadedData_thenShowError() = runTest(testDispatcher) {
        launchScreen()
        coEvery { useCase.invoke() } returns PresentationDataMock.genericResponseErrorMock
        testDispatcher.scheduler.runCurrent()

        composeTestRule.run {
            onNodeWithTag("${TAG_TEST_USER_ITEM}${ID_USER}").assertDoesNotExist()
            onNodeWithTag("${TAG_TEST_USER_NAME}${NAME_USER}").assertDoesNotExist()
            onNodeWithTag("${TAG_TEST_USER_USERNAME}${USERNAME}").assertDoesNotExist()
            onNodeWithTag("${TAG_TEST_USER_IMAGE}${IMG_USER}").assertDoesNotExist()
            onNodeWithTag(ERROR_MESSAGE_VIEW).assertIsDisplayed()
            onNodeWithText(GENERIC_ERROR).assertIsDisplayed()
            onNodeWithTag(LOADING_VIEW_TEST_TAG).assertDoesNotExist()
        }

    }

    @Test
    fun givenLoadingState_whenScreenComposed_thenShowLoadingView() = runTest(testDispatcher) {
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
