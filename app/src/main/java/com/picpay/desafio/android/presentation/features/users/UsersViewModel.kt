package com.picpay.desafio.android.presentation.features.users

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import br.com.victorcs.core.base.BaseViewModel
import br.com.victorcs.core.constants.GENERIC_MESSAGE_ERROR
import br.com.victorcs.core.domain.model.Response
import br.com.victorcs.core.providers.IDispatchersProvider
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecases.IFetchUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

private const val STOP_TIMER_LIMIT = 5000L

class UsersViewModel(
    private val useCase: IFetchUsersUseCase,
    dispatchers: IDispatchersProvider
) : BaseViewModel(dispatchers) {

    private val _state = MutableStateFlow(UsersScreenState())

    val screenState: StateFlow<UsersScreenState> = _state
        .onStart {
            fetchUsers()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = STOP_TIMER_LIMIT),
            initialValue = UsersScreenState().copy(isLoading = true)
        )

    fun execute(intent: UsersIntent) = when (intent) {
        is UsersIntent.FetchUsers -> fetchUsers()
    }

    private fun fetchUsers() {
        launch(
            block = {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                val usersResponse = useCase.invoke()

                when (usersResponse) {
                    is Response.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            users = usersResponse.data,
                            errorMessage = null
                        )
                    }

                    is Response.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = usersResponse.errorMessage
                        )
                    }
                }
            },

            errorBlock = { error ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = error.message ?: GENERIC_MESSAGE_ERROR
                )
                Unit
            },
        )
    }
}

@Stable
@Immutable
data class UsersScreenState(
    val users: List<User>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
