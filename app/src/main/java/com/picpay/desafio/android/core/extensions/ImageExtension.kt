package com.picpay.desafio.android.core.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.picpay.desafio.android.R
import timber.log.Timber

private const val GLIDE_TAG = "GLIDE_TAG"

fun AppCompatImageView.loadImage(
    imageUrl: String?,
    forceCache: Boolean = false,
    transformCircle: Boolean = false,
) = try {
    val requestOptions = RequestOptions().run {
        error(R.drawable.ic_round_account_circle).transform(FitCenter())
    }

    val cacheStrategy = if (forceCache) DiskCacheStrategy.ALL else DiskCacheStrategy.AUTOMATIC

    this.tag = null

    if (transformCircle) {
        Glide.with(this.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .transform(CircleCrop())
            .diskCacheStrategy(cacheStrategy)
            .into(this)
    } else {
        Glide.with(this.context)
            .setDefaultRequestOptions(requestOptions)
            .load(imageUrl)
            .diskCacheStrategy(cacheStrategy)
            .into(this)
    }
} catch (ex: Exception) {
    Timber.tag(GLIDE_TAG).e(ex)
}
