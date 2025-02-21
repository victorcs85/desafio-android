package com.picpay.desafio.android.core.extensions

import android.content.Context
import android.widget.Toast

fun Context.showError(message: String) = Toast.makeText(
    this,
    message,
    Toast.LENGTH_SHORT
).show()