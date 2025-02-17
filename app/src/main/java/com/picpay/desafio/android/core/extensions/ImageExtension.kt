package com.picpay.desafio.android.core.extensions


import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.picpay.desafio.android.R
import timber.log.Timber

private const val GLIDE_TAG = "GLIDE_TAG"

fun AppCompatImageView.loadImage(
    imageUrl: String?,
    transformCircle: Boolean = false,
    callback: RequestListener<Drawable>? = null
) = try {
    val requestOptions = RequestOptions().run {
        error(R.drawable.ic_round_account_circle).transform(FitCenter())
    }

    if (transformCircle) {
        Glide.with(this.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .transform(CircleCrop())
            .listener(callback)
            .into(this)
    } else {
        Glide.with(this.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .listener(callback)
            .into(this)
    }
} catch (ex: Exception) {
    Timber.tag(GLIDE_TAG).e(ex)
}
