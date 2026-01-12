package com.driverhub.shared.data.api

import com.driverhub.shared.domain.model.*
import com.driverhub.shared.domain.repository.AuthApi
import com.driverhub.shared.domain.repository.TokenStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

/**
 * Backend error response structure
 */
@Serializable
data class ApiErrorResponse(
    val error: String,
    val message: String
)

/**
 * Auth API Implementation using Ktor HTTP Client
 * Connects to backend at http://10.195.53.108:3000/api/v1/auth
 */
class AuthApiImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage,  // ADDED: For getting access token
    private val baseUrl: String = "http://10.195.53.108:3000/api/v1/auth"
) : AuthApi {
    
    /**
     * Helper function to handle HTTP responses and extract errors
     * This ensures users only see backend error messages, NOT IP addresses or URLs
     */
    private suspend inline fun <reified T> handleResponse(response: HttpResponse): T {
        return if (response.status.isSuccess()) {
            response.body<T>()
        } else {
            // Parse backend error response
            val errorResponse = try {
                response.body<ApiErrorResponse>()
            } catch (e: Exception) {
                // If we can't parse the error, show generic message
                ApiErrorResponse(
                    error = "UNKNOWN_ERROR",
                    message = "An unexpected error occurred. Please try again."
                )
            }
            
            // Throw exception with ONLY the backend message (no IP/URL/path)
            throw Exception(errorResponse.message)
        }
    }
    
    /**
     * Helper to add Authorization header if access token exists
     */
    private suspend fun HttpRequestBuilder.addAuthHeader() {
        val accessToken = tokenStorage.getAccessToken()
        if (!accessToken.isNullOrBlank()) {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }
    
    override suspend fun register(request: RegisterRequest): RegisterResponse {
        val response = client.post("$baseUrl/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = client.post("$baseUrl/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun checkAccount(request: CheckAccountRequest): CheckAccountResponse {
        val response = client.post("$baseUrl/check-account") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun sendOtp(request: SendOtpRequest): SendOtpResponse {
        val response = client.post("$baseUrl/send-otp") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun verifyOtp(request: VerifyOtpRequest): VerifyOtpResponse {
        val response = client.post("$baseUrl/verify-otp") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun resetPassword(request: ResetPasswordRequest): MessageResponse {
        val response = client.post("$baseUrl/reset-password") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun changePassword(request: ChangePasswordRequest): MessageResponse {
        val response = client.patch("$baseUrl/change-password") {
            contentType(ContentType.Application.Json)
            addAuthHeader()  // ← ADD AUTH HEADER (protected endpoint)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        val response = client.post("$baseUrl/refresh-token") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun logout(request: LogoutRequest): MessageResponse {
        val response = client.post("$baseUrl/logout") {
            contentType(ContentType.Application.Json)
            addAuthHeader()  // ← ADD AUTH HEADER (protected endpoint)
            setBody(request)
        }
        return handleResponse(response)
    }
    
    override suspend fun logoutAll(): MessageResponse {
        val response = client.post("$baseUrl/logout-all") {
            contentType(ContentType.Application.Json)
            addAuthHeader()  // ← ADD AUTH HEADER (protected endpoint)
        }
        return handleResponse(response)
    }
}
