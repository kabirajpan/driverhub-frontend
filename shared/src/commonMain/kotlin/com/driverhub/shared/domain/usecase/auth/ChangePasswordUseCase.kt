package com.driverhub.shared.domain.usecase.auth

import com.driverhub.shared.domain.model.MessageResponse
import com.driverhub.shared.domain.repository.AuthRepository

/**
 * Change Password Use Case
 * Handles password change for authenticated users
 */
class ChangePasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Result<MessageResponse> {
        // Validate inputs
        if (currentPassword.isBlank()) {
            return Result.failure(Exception("Current password is required"))
        }
        
        if (newPassword.isBlank()) {
            return Result.failure(Exception("New password is required"))
        }
        
        if (newPassword.length < 8) {
            return Result.failure(Exception("New password must be at least 8 characters"))
        }
        
        if (newPassword != confirmPassword) {
            return Result.failure(Exception("Passwords do not match"))
        }
        
        if (currentPassword == newPassword) {
            return Result.failure(Exception("New password must be different from current password"))
        }
        
        // Call repository to change password
        return authRepository.changePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
    }
}
