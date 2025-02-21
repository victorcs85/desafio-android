package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.ChallengeInitialization
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class ChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        setUpTimber()
    }

    private fun setUpKoin() =
        startKoin {
            androidLogger()
            androidContext(this@ChallengeApplication)
            modules(
                ChallengeInitialization().init()
            )
        }

    private fun setUpTimber() =
        Timber.plant(Timber.DebugTree())
}