package com.picpay.desafio.android.data.source.remote.exceptions

import com.picpay.desafio.android.core.constants.NETWORK_ERROR
import java.io.IOException

class WithoutNetworkException : IOException(NETWORK_ERROR)
