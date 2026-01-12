package com.driverhub.shared.domain.usecase.auth

import com.driverhub.shared.domain.model.MessageResponse
import com.driverhub.shared.domain.model.RefreshTokenResponse
import com.driverhub.shared.domain.repository.AuthRepository

/**
 * Session Use Case
 * Handles token refresh, logout, and session management
 */
class SessionUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Refresh access token using refresh token
     */
    suspend fun refreshToken(): Result<RefreshTokenResponse> {
        val result = authRepository.refreshToken()
        
        // Save new tokens on success
        result.onSuccess { response ->
            authRepository.saveTokens(response.accessToken, response.refreshToken)
        }
        
        return result
    }
    
    /**
     * Logout from current device
     */
    suspend fun logout(): Result<MessageResponse> {
        val refreshToken = authRepository.getRefreshToken()
            ?: return Result.failure(Exception("No refresh token found"))
        
        val result = authRepository.logout(refreshToken)
        
        // Clear tokens on success
        result.onSuccess {
            authRepository.clearTokens()
        }
        
        return result
    }
    
    /**
     * Logout from all devices
     */
    suspend fun logoutAll(): Result<MessageResponse> {
        val result = authRepository.logoutAll()
        
        // Clear tokens on success
        result.onSuccess {
            authRepository.clearTokens()
        }
        
        return result
    }
    
    /**
     * Check if user is logged in (has valid tokens)
     */
    suspend fun isLoggedIn(): Boolean {
        val accessToken = authRepository.getAccessToken()
        return !accessToken.isNullOrBlank()
    }
    
    /**
     * Get current access token
     */
    suspend fun getAccessToken(): String? {
        return authRepository.getAccessToken()
    }
    
    /**
     * Clear all tokens (force logout locally)
     */
    suspend fun clearTokens() {
        authRepository.clearTokens()
    }
}
