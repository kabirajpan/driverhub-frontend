package com.driverhub.shared.di

import com.driverhub.shared.data.api.AuthApiImpl
import com.driverhub.shared.data.api.HttpClientFactory
import com.driverhub.shared.data.repository.CarRepositoryImpl
import com.driverhub.shared.domain.repository.*
import com.driverhub.shared.domain.usecase.auth.*
import com.driverhub.shared.domain.usecase.owner.cars.*
import com.driverhub.shared.presentation.auth.login.LoginViewModel
import com.driverhub.shared.presentation.auth.register.RegisterViewModel
import com.driverhub.shared.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.driverhub.shared.presentation.owner.cars.ActiveCarsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin Dependency Injection Module
 * Defines how to create and wire all dependencies
 */
val appModule = module {
    
    // ========== HTTP Client ==========
    single { HttpClientFactory.create() }
    
    // ========== API ==========
    single<AuthApi> { 
        AuthApiImpl(
            client = get(),
            tokenStorage = get()  // ← FIXED: Added tokenStorage parameter
        ) 
    }
    
    // ========== Repositories ==========
    single<AuthRepository> { 
        AuthRepositoryImpl(
            authApi = get(),
            tokenStorage = get()
        ) 
    }
    
    single<CarRepository> { CarRepositoryImpl() }
    
    // ========== Use Cases - Auth ==========
    factory { LoginUseCase(authRepository = get(), tokenStorage = get()) }
    factory { RegisterUseCase(authRepository = get()) }
    factory { ForgotPasswordUseCase(authRepository = get()) }
    factory { SessionUseCase(authRepository = get()) }
    
    // ========== Use Cases - Owner ==========
    factory { GetActiveCarsUseCase(carRepository = get()) }
    factory { GetAllCarsUseCase(carRepository = get()) }
    
    // ========== ViewModels - Auth ==========
    factory { LoginViewModel(loginUseCase = get()) }
    factory { RegisterViewModel(registerUseCase = get()) }
    factory { ForgotPasswordViewModel(forgotPasswordUseCase = get()) }
    
    // ========== ViewModels - Owner ==========
    factory { 
        ActiveCarsViewModel(
            getActiveCarsUseCase = get(),
            getAllCarsUseCase = get()
        )
    }
}

/**
 * Platform-specific module for TokenStorage
 * This will be defined per platform (Android/iOS)
 */
expect val platformModule: Module
