package com.picpay.desafio.android.presentation.ui.users

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.picpay.desafio.android.core.constants.LOCAL_SOURCE
import com.picpay.desafio.android.core.constants.REMOTE_SOURCE
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.extensions.isTextDisplayed
import com.picpay.desafio.android.presentation.ui.users.viewmodel.UsersViewModel
import com.picpay.desafio.android.shared.test.PresentationDataMock
import io.mockk.coEvery
import io.mockk.mockk
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject

private const val TITLE_SCREEN = "Contatos"
private const val NAME_USER = "Eduardo Santos"

fun MainActivityTest.withMainActivity(func: MainActivityRobot.() -> Unit) =
    MainActivityRobot().apply(func)

class MainActivityRobot : KoinTest {

    private val usersViewModel: UsersViewModel = mockk()
    private val localRepository by inject<UserRepository>(named(LOCAL_SOURCE))
    private val remoteRepository by inject<UserRepository>(named(REMOTE_SOURCE))

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

    fun launchActivity() = ActivityScenario.launch<MainActivity>(
        Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
    )

    infix fun verify(func: MainActivityResult.() -> Unit) = MainActivityResult().apply(func)
}

class MainActivityResult {
    fun checkScreenTitleDisplayed() = TITLE_SCREEN.isTextDisplayed()
    fun checkNameUserDisplayed() = NAME_USER.isTextDisplayed()
}