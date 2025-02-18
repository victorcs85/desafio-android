package com.picpay.desafio.android.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {

    protected fun launch(
        errorBlock: ((Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch {
        runCatching {
            block()
        }.onFailure { error ->
            Timber.e(error)
            errorBlock?.invoke(error)
        }
    }
}