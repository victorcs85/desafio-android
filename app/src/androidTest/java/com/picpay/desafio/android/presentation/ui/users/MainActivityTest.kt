package com.picpay.desafio.android.presentation.ui.users

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.picpay.desafio.android.core.DisableAnimationsRule
import com.picpay.desafio.android.core.KoinRuleHelper
import com.picpay.desafio.android.di.ModuleInitializer
import com.picpay.desafio.android.di.repositoryMockModules
import com.picpay.desafio.android.di.viewModelMockModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @get:Rule
    val koinRule = KoinRuleHelper(
        ModuleInitializer.modules +
                viewModelMockModules +
                repositoryMockModules
    )

    @Test
    fun givenUsers_whenLoadMainActivity_thenLoadDataSuccessfully() {
        withMainActivity {
            mockUsers()
            launchActivity()
        } verify {
            checkScreenTitleDisplayed()
            checkNameUserDisplayed()
        }
    }

    @Test
    fun givenNoUsers_whenLoadMainActivity_thenShowEmptyState() {
        withMainActivity {
            mockUsers(hasRemoteData = false)
            launchActivity()
        } verify {
            checkEmptyStateDisplayed()
        }
    }

    @Test
    fun givenUsers_whenRotateDeviceScreen_thenShowUsersListSuccessfully() {
        withMainActivity {
            mockUsers()
            launchActivity()
        } actions {
            rotateScreen(ScreenPosition.LANDSCAPE)
            rotateScreen(ScreenPosition.PORTRAIT)
        } verify {
            checkNameUserDisplayed()
        }
    }
}