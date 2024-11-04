package com.farmiot.smartagriculture

import android.app.Application
import com.farmiot.smartagriculture.repository.di.repositoryModule
import com.farmiot.smartagriculture.ui.viewmodel.viewModelModule
import com.farmiot.smartagriculture.utils.utilsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {


    override fun onCreate() {
        super.onCreate()

        val modules = listOf(
            viewModelModule,
            repositoryModule,
            utilsModule
        )
        startKoin {
            androidContext(this@Application)
            modules(modules)
        }
    }

    companion object {
        const val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"
    }
}