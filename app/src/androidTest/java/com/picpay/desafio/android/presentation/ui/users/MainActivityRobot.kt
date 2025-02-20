package com.picpay.desafio.android.presentation.ui.users

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.constants.LOCAL_SOURCE
import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.extensions.checkNumberItems
import com.picpay.desafio.android.extensions.isTextDisplayed
import com.picpay.desafio.android.shared.test.PresentationDataMock
import io.mockk.coEvery
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject

private const val TITLE_SCREEN = "Contatos"
private const val NAME_USER = "Eduardo Santos"
private const val ZERO = 0

fun MainActivityTest.withMainActivity(func: MainActivityRobot.() -> Unit) =
    MainActivityRobot().apply(func)

class MainActivityRobot : KoinTest {

    private val localRepository by inject<UserRepository>(named(LOCAL_SOURCE))
    private val remoteRepository by inject<UserRepository>(named(REMOTE_SOURCE))

    //region Mocks
    fun mockUsers(
        hasRemoteData: Boolean = true,
        hasLocalData: Boolean = true
    ) {
        coEvery { remoteRepository.getUsers() } returns
                if (hasRemoteData)
                    PresentationDataMock.USERS_MOCK
                else
                    emptyList()

        coEvery { localRepository.getUsers() } returns
                if (hasLocalData)
                    PresentationDataMock.USERS_MOCK
                else
                    emptyList()
    }
    //endregion

    fun launchActivity() {
        ActivityScenario.launch<MainActivity>(
            Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        )
    }

    infix fun actions(func: MainActivityRobot.() -> Unit) = this.apply(func)

    infix fun verify(func: MainActivityResult.() -> Unit) = MainActivityResult().apply(func)

    //region Actions
    fun rotateScreen(screenPosition: ScreenPosition = ScreenPosition.PORTRAIT) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        when(screenPosition) {
            ScreenPosition.PORTRAIT -> device.setOrientationNatural()
            ScreenPosition.LANDSCAPE -> device.setOrientationLeft()
        }
    }
    //endregion
}

class MainActivityResult {
    fun checkScreenTitleDisplayed() = TITLE_SCREEN.isTextDisplayed()
    fun checkNameUserDisplayed() = NAME_USER.isTextDisplayed()
    fun checkEmptyStateDisplayed() {
        R.id.rv_users.checkNumberItems(quantity = ZERO)
    }
}

enum class ScreenPosition(val value: Int) {
    PORTRAIT(0), LANDSCAPE(1)
}