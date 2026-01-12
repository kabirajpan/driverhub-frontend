package com.driverhub.shared.domain.usecase.auth

import com.driverhub.shared.domain.model.RegisterResponse
import com.driverhub.shared.domain.repository.AuthRepository

/**
 * Register Use Case
 * Handles user registration and token storage
 */
class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        emailPhone: String,
        password: String,
        confirmPassword: String,
        role: String,
        name: String
    ): Result<RegisterResponse> {
        // Validate input
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        if (name.isBlank()) {
            return Result.failure(Exception("Name is required"))
        }
        if (password.length < 8) {
            return Result.failure(Exception("Password must be at least 8 characters"))
        }
        if (password != confirmPassword) {
            return Result.failure(Exception("Passwords do not match"))
        }
        if (role.isBlank()) {
            return Result.failure(Exception("Please select a role"))
        }
        
        // Determine if input is email or phone
        val isEmail = emailPhone.contains("@")
        
        val result = if (isEmail) {
            authRepository.register(
                email = emailPhone,
                phone = null,
                password = password,
                confirmPassword = confirmPassword,
                role = role,
                name = name
            )
        } else {
            authRepository.register(
                email = null,
                phone = emailPhone,
                password = password,
                confirmPassword = confirmPassword,
                role = role,
                name = name
            )
        }
        
        // Save tokens on success
        result.onSuccess { response ->
            authRepository.saveTokens(response.token, response.refreshToken)
        }
        
        return result
    }
}
