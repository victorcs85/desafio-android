package com.picpay.desafio.android.di

import org.koin.core.module.Module

object ModuleInitializer {
    val modules = mutableListOf<Module>()

    fun add(modules: List<Module>) {
        ModuleInitializer.modules.addAll(modules)
    }
}