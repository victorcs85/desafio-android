package com.picpay.desafio.android.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import timber.log.Timber

abstract class BaseViewModel : ViewModel(), KoinComponent {

    private var loading = MutableLiveData<Boolean>()

    protected fun launch(
        enableLoading: Boolean = true,
        errorBlock: ((Throwable) -> Unit?)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) =
        viewModelScope.launch {
            if (enableLoading) loading.postValue(true)
            runCatching {
                block()
            }
                .onSuccess { if (enableLoading) loading.postValue(false) }
                .onFailure { error ->
                    if (enableLoading) loading.postValue(false)
                    Timber.e(error)
                    errorBlock?.invoke(error)
                }
        }
}