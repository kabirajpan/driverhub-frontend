package com.driverhub.shared.domain.repository

import com.driverhub.shared.domain.model.*

/**
 * Auth Repository Interface
 */
interface AuthRepository {
    // Authentication
    suspend fun register(
        email: String?,
        phone: String?,
        password: String,
        confirmPassword: String,
        role: String,
        name: String
    ): Result<RegisterResponse>
    
    suspend fun login(
        email: String?,
        phone: String?,
        password: String
    ): Result<LoginResponse>
    
    // Account Recovery
    suspend fun checkAccount(
        email: String?,
        phone: String?
    ): Result<CheckAccountResponse>
    
    suspend fun sendOtp(
        email: String?,
        phone: String?,
        purpose: String
    ): Result<SendOtpResponse>
    
    suspend fun verifyOtp(
        email: String?,
        phone: String?,
        otpCode: String,
        purpose: String
    ): Result<VerifyOtpResponse>
    
    suspend fun resetPassword(
        email: String?,
        phone: String?,
        otpCode: String,
        newPassword: String,
        confirmPassword: String
    ): Result<MessageResponse>
    
    // Password Management
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<MessageResponse>
    
    // Session Management
    suspend fun refreshToken(): Result<RefreshTokenResponse>
    suspend fun logout(refreshToken: String): Result<MessageResponse>
    suspend fun logoutAll(): Result<MessageResponse>
    
    // Token Storage
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()
}

/**
 * Auth Repository Implementation
 * Uses AuthApi for network calls and TokenStorage for local storage
 */
class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    
    override suspend fun register(
        email: String?,
        phone: String?,
        password: String,
        confirmPassword: String,
        role: String,
        name: String
    ): Result<RegisterResponse> = runCatching {
        val authProvider = if (email != null) "EMAIL" else "PHONE"
        val request = RegisterRequest(
            email = email,
            phone = phone,
            password = password,
            confirmPassword = confirmPassword,
            authProvider = authProvider,
            role = role,
            name = name
        )
        authApi.register(request)
    }
    
    override suspend fun login(
        email: String?,
        phone: String?,
        password: String
    ): Result<LoginResponse> = runCatching {
        val request = LoginRequest(
            email = email,
            phone = phone,
            password = password
        )
        authApi.login(request)
    }
    
    override suspend fun checkAccount(
        email: String?,
        phone: String?
    ): Result<CheckAccountResponse> = runCatching {
        val request = CheckAccountRequest(email = email, phone = phone)
        authApi.checkAccount(request)
    }
    
    override suspend fun sendOtp(
        email: String?,
        phone: String?,
        purpose: String
    ): Result<SendOtpResponse> = runCatching {
        val request = SendOtpRequest(
            email = email,
            phone = phone,
            purpose = purpose
        )
        authApi.sendOtp(request)
    }
    
    override suspend fun verifyOtp(
        email: String?,
        phone: String?,
        otpCode: String,
        purpose: String
    ): Result<VerifyOtpResponse> = runCatching {
        val request = VerifyOtpRequest(
            email = email,
            phone = phone,
            otpCode = otpCode,
            purpose = purpose
        )
        authApi.verifyOtp(request)
    }
    
    override suspend fun resetPassword(
        email: String?,
        phone: String?,
        otpCode: String,
        newPassword: String,
        confirmPassword: String
    ): Result<MessageResponse> = runCatching {
        val request = ResetPasswordRequest(
            email = email,
            phone = phone,
            otpCode = otpCode,
            newPassword = newPassword,
            confirmPassword = confirmPassword
        )
        authApi.resetPassword(request)
    }
    
    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<MessageResponse> = runCatching {
        val request = ChangePasswordRequest(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
        authApi.changePassword(request)
    }
    
    override suspend fun refreshToken(): Result<RefreshTokenResponse> = runCatching {
        val refreshToken = getRefreshToken() 
            ?: throw Exception("No refresh token found")
        val request = RefreshTokenRequest(refreshToken = refreshToken)
        authApi.refreshToken(request)
    }
    
    override suspend fun logout(refreshToken: String): Result<MessageResponse> = runCatching {
        val request = LogoutRequest(refreshToken = refreshToken)
        authApi.logout(request)
    }
    
    override suspend fun logoutAll(): Result<MessageResponse> = runCatching {
        authApi.logoutAll()
    }
    
    // Token Management
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenStorage.saveTokens(accessToken, refreshToken)
    }
    
    override suspend fun getAccessToken(): String? {
        return tokenStorage.getAccessToken()
    }
    
    override suspend fun getRefreshToken(): String? {
        return tokenStorage.getRefreshToken()
    }
    
    override suspend fun clearTokens() {
        tokenStorage.clearTokens()
    }
}

/**
 * AuthApi Interface - will be implemented with Ktor
 */
interface AuthApi {
    suspend fun register(request: RegisterRequest): RegisterResponse
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun checkAccount(request: CheckAccountRequest): CheckAccountResponse
    suspend fun sendOtp(request: SendOtpRequest): SendOtpResponse
    suspend fun verifyOtp(request: VerifyOtpRequest): VerifyOtpResponse
    suspend fun resetPassword(request: ResetPasswordRequest): MessageResponse
    suspend fun changePassword(request: ChangePasswordRequest): MessageResponse
    suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse
    suspend fun logout(request: LogoutRequest): MessageResponse
    suspend fun logoutAll(): MessageResponse
}

/**
 * TokenStorage - platform-specific implementation (expect/actual)
 */
expect class TokenStorage {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()
    suspend fun saveUserRole(role: String)  // ADD THIS
    suspend fun getUserRole(): String?      // ADD THIS
}
