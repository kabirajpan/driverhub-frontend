package com.driverhub.shared.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ========== Auth Tokens ==========
@Serializable
data class AuthTokens(
    val accessToken: String,
    val refreshToken: String
)

// ========== Response Models ==========
@Serializable
data class LoginResponse(
    val message: String,
    val user: User,
    val token: String,
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
data class RegisterResponse(
    val message: String,
    val user: User,
    val token: String,
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
data class CheckAccountResponse(
    val exists: Boolean,
    @SerialName("masked_identifier")
    val maskedIdentifier: String? = null,
    @SerialName("available_methods")
    val availableMethods: List<String> = emptyList()
)

@Serializable
data class SendOtpResponse(
    val message: String,
    val identifier: String
)

@Serializable
data class VerifyOtpResponse(
    val message: String,
    @SerialName("is_verified")
    val isVerified: Boolean
)

@Serializable
data class MessageResponse(
    val message: String
)

@Serializable
data class RefreshTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String
)

// ========== Request Models ==========
@Serializable
data class RegisterRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String,
    @SerialName("confirm_password")
    val confirmPassword: String,
    @SerialName("auth_provider")
    val authProvider: String,
    val role: String,
    val name: String
)

@Serializable
data class LoginRequest(
    val email: String? = null,
    val phone: String? = null,
    val password: String
)

@Serializable
data class CheckAccountRequest(
    val email: String? = null,
    val phone: String? = null
)

@Serializable
data class SendOtpRequest(
    val email: String? = null,
    val phone: String? = null,
    val purpose: String // EMAIL_VERIFICATION, PHONE_VERIFICATION, PASSWORD_RESET
)

@Serializable
data class VerifyOtpRequest(
    val email: String? = null,
    val phone: String? = null,
    @SerialName("otp_code")
    val otpCode: String,
    val purpose: String
)

@Serializable
data class ResetPasswordRequest(
    val email: String? = null,
    val phone: String? = null,
    @SerialName("otp_code")
    val otpCode: String,
    @SerialName("new_password")
    val newPassword: String,
    @SerialName("confirm_password")
    val confirmPassword: String
)

@Serializable
data class ChangePasswordRequest(
    @SerialName("current_password")
    val currentPassword: String,
    @SerialName("new_password")
    val newPassword: String
)

@Serializable
data class RefreshTokenRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)

@Serializable
data class LogoutRequest(
    @SerialName("refresh_token")
    val refreshToken: String
)
