package com.github.ivankornienko31.stepikclientapplication.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    platformDependencies: Module, // Сюда мы передадим платформозависимые штуки (DataStore)
    appDeclaration: KoinAppDeclaration = {}
) {
    startKoin {
        appDeclaration()
        modules(sharedModules + platformDependencies)
    }
}