package com.picpay.desafio.android.presentation.ui.users

import androidx.test.filters.MediumTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.core.DisableAnimationsRule
import com.picpay.desafio.android.core.KoinRuleHelper
import com.picpay.desafio.android.di.ModuleInitializer
import com.picpay.desafio.android.di.repositoryMockModules
import org.junit.BeforeClass
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
}