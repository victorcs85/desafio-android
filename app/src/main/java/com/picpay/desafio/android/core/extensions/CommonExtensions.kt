package com.picpay.desafio.android.core.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.picpay.desafio.android.R
import kotlin.system.exitProcess

fun Context.showDialogWithTitleAndPositiveAndNegative(
    dialogTitle: String,
    dialogMessage: String,
    positiveMessage: String,
    negativeMessage: String,
    cancelable: Boolean = false,
    positiveListener: (() -> Unit),
    negativeListener: (() -> Unit)?,
) {
    val dialog: AlertDialog = AlertDialog.Builder(this)
        .setTitle(dialogTitle)
        .setMessage(dialogMessage)
        .setCancelable(cancelable)
        .setPositiveButton(positiveMessage) { dialog, _ ->
            positiveListener()
            dialog.dismiss()
        }
        .setNegativeButton(negativeMessage) { dialog, _ ->
            negativeListener?.invoke()
            dialog.dismiss()
        }
        .create()

    dialog.show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE)

    dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
}

fun Context.showAlertDialog(message: String) = AlertDialog.Builder(this)
    .setTitle(getString(R.string.error_title))
    .setMessage(message)
    .setCancelable(false)
    .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
        dialog.dismiss()
    }
    .create().also { dialog ->

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    }

fun Context.showNetworkErrorDialog(activity: Activity, action: (() -> Unit)? = null) {
    showDialogWithTitleAndPositiveAndNegative(
        dialogTitle = getString(R.string.network_title_error),
        dialogMessage = getString(R.string.network_description_error),
        positiveMessage = getString(R.string.try_again),
        negativeMessage = getString(android.R.string.cancel),
        positiveListener = { action?.invoke() },
        negativeListener = {
            activity.finishAffinity()
            exitProcess(0)
        },
    )
}

fun Context.showError(message: String) = Toast.makeText(
    this,
    message,
    Toast.LENGTH_SHORT
).show()