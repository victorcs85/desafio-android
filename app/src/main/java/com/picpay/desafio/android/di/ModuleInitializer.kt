package com.picpay.desafio.android.di

import org.koin.core.module.Module

object ModuleInitializer {
    private val modules = mutableListOf<Module>()

    fun add(modules: List<Module>) {
        ModuleInitializer.modules.addAll(modules)
    }
}