package com.driverhub.shared.domain.usecase.auth

import com.driverhub.shared.domain.model.LoginResponse
import com.driverhub.shared.domain.repository.AuthRepository
import com.driverhub.shared.domain.repository.TokenStorage

/**
 * Login Use Case
 * Handles user login and token storage
 */
class LoginUseCase(
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(
        emailPhone: String,
        password: String
    ): Result<LoginResponse> {
        // Validate input
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        if (password.isBlank()) {
            return Result.failure(Exception("Password is required"))
        }

        // Determine if input is email or phone
        val isEmail = emailPhone.contains("@")
        
        val result = if (isEmail) {
            authRepository.login(email = emailPhone, phone = null, password = password)
        } else {
            authRepository.login(email = null, phone = emailPhone, password = password)
        }

        // Save tokens AND user role on success
        result.onSuccess { response ->
            authRepository.saveTokens(response.token, response.refreshToken)
            tokenStorage.saveUserRole(response.user.role.name)
        }

        return result
    }
}
