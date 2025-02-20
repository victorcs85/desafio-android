package com.picpay.desafio.android.core

import androidx.test.core.app.ApplicationProvider
import io.mockk.unmockkAll
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinRuleHelper(
    private val modules: List<Module> = emptyList(),
    private val shouldStart: Boolean = true,
    private val shouldStop: Boolean = true,
) : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    if (shouldStart) {
                        stopKoin()
                        startKoin {
                            androidContext(
                                ApplicationProvider.getApplicationContext(),
                            )

                            modules(
                                modules
                            )
                        }
                    } else {
                        loadKoinModules(modules)
                    }

                    base?.evaluate()
                } finally {
                    unmockkAll()

                    if (shouldStart) {
                        if (shouldStop) {
                            stopKoin()
                        }
                    } else {
                        unloadKoinModules(modules)
                    }
                }
            }
        }
    }
}