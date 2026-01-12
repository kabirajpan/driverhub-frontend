package com.driverhub.shared.domain.usecase.auth

import com.driverhub.shared.domain.model.*
import com.driverhub.shared.domain.repository.AuthRepository

/**
 * Forgot Password Use Case
 * Combines check account, send OTP, verify OTP, and reset password operations
 */
class ForgotPasswordUseCase(
    private val authRepository: AuthRepository
) {
    
    /**
     * Step 1: Check if account exists
     */
    suspend fun checkAccount(emailPhone: String): Result<CheckAccountResponse> {
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        
        val isEmail = emailPhone.contains("@")
        
        return if (isEmail) {
            authRepository.checkAccount(email = emailPhone, phone = null)
        } else {
            authRepository.checkAccount(email = null, phone = emailPhone)
        }
    }
    
    /**
     * Step 2: Send OTP for password reset
     */
    suspend fun sendOtp(emailPhone: String): Result<SendOtpResponse> {
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        
        val isEmail = emailPhone.contains("@")
        
        return if (isEmail) {
            authRepository.sendOtp(
                email = emailPhone,
                phone = null,
                purpose = "PASSWORD_RESET"
            )
        } else {
            authRepository.sendOtp(
                email = null,
                phone = emailPhone,
                purpose = "PASSWORD_RESET"
            )
        }
    }
    
    /**
     * Step 3: Verify OTP
     */
    suspend fun verifyOtp(
        emailPhone: String,
        otpCode: String
    ): Result<VerifyOtpResponse> {
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        if (otpCode.isBlank()) {
            return Result.failure(Exception("OTP code is required"))
        }
        
        val isEmail = emailPhone.contains("@")
        
        return if (isEmail) {
            authRepository.verifyOtp(
                email = emailPhone,
                phone = null,
                otpCode = otpCode,
                purpose = "PASSWORD_RESET"
            )
        } else {
            authRepository.verifyOtp(
                email = null,
                phone = emailPhone,
                otpCode = otpCode,
                purpose = "PASSWORD_RESET"
            )
        }
    }
    
    /**
     * Step 4: Reset password with OTP
     */
    suspend fun resetPassword(
        emailPhone: String,
        otpCode: String,
        newPassword: String,
        confirmPassword: String
    ): Result<MessageResponse> {
        if (emailPhone.isBlank()) {
            return Result.failure(Exception("Email or phone is required"))
        }
        if (otpCode.isBlank()) {
            return Result.failure(Exception("OTP code is required"))
        }
        if (newPassword.length < 8) {
            return Result.failure(Exception("Password must be at least 8 characters"))
        }
        if (newPassword != confirmPassword) {
            return Result.failure(Exception("Passwords do not match"))
        }
        
        val isEmail = emailPhone.contains("@")
        
        return if (isEmail) {
            authRepository.resetPassword(
                email = emailPhone,
                phone = null,
                otpCode = otpCode,
                newPassword = newPassword,
                confirmPassword = confirmPassword
            )
        } else {
            authRepository.resetPassword(
                email = null,
                phone = emailPhone,
                otpCode = otpCode,
                newPassword = newPassword,
                confirmPassword = confirmPassword
            )
        }
    }
}
