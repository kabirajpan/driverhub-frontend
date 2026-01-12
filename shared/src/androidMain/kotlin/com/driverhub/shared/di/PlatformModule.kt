package com.driverhub.shared.di

import com.driverhub.shared.domain.repository.TokenStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Android Platform Module
 * Provides Android-specific dependencies like TokenStorage
 */
actual val platformModule = module {
    single { TokenStorage(androidContext()) }
}
