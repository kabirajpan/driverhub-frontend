package com.driverhub.app

import android.app.Application
import com.driverhub.shared.di.appModule
import com.driverhub.shared.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DriverHubApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@DriverHubApplication)
            modules(appModule, platformModule)
        }
    }
}
