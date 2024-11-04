package com.farmiot.smartagriculture.repository.di

import com.farmiot.smartagriculture.repository.AuthAppRepository
import com.farmiot.smartagriculture.repository.MainRepository
import org.koin.dsl.module

val repositoryModule = module {
    // Repositories
    single { MainRepository() }
    single { AuthAppRepository(get(), get()) }
}
