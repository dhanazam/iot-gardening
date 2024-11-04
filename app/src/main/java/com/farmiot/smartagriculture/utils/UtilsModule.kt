package com.farmiot.smartagriculture.utils

import org.koin.dsl.module

val utilsModule = module {
    factory { SettingsUtility(get()) }
}