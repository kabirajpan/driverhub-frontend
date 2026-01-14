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
    private val tokenStorage: TokenStorage,
    private val baseUrl: String = "http://10.195.53.108:3000/api/v1/auth"
) : AuthApi {

    /**
     * Helper function to handle HTTP responses and extract errors
     */
    private suspend inline fun <reified T> handleResponse(response: HttpResponse): T {
        return if (response.status.isSuccess()) {
            response.body<T>()
        } else {
            val errorResponse = try {
                response.body<ApiErrorResponse>()
            } catch (e: Exception) {
                ApiErrorResponse(
                    error = "UNKNOWN_ERROR",
                    message = "An unexpected error occurred. Please try again."
                )
            }
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

    /**
     * Helper to make authenticated requests with automatic token refresh
     * If we get 401, it tries to refresh the token and retry once
     */
    private suspend inline fun <reified T> makeAuthenticatedRequest(
        crossinline block: suspend () -> HttpResponse
    ): T {
        var response = block()
        
        // If we get 401 (Unauthorized), try to refresh token and retry once
        if (response.status == HttpStatusCode.Unauthorized) {
            val refreshToken = tokenStorage.getRefreshToken()
            
            if (!refreshToken.isNullOrBlank()) {
                try {
                    // Try to refresh the token
                    val refreshResponse = client.post("$baseUrl/refresh-token") {
                        contentType(ContentType.Application.Json)
                        setBody(mapOf("refresh_token" to refreshToken))
                    }
                    
                    if (refreshResponse.status.isSuccess()) {
                        // Parse and save new tokens
                        val tokenResponse: RefreshTokenResponse = refreshResponse.body()
                        tokenStorage.saveTokens(
                            tokenResponse.accessToken,
                            tokenResponse.refreshToken
                        )
                        
                        // Retry the original request with new token
                        response = block()
                    } else {
                        // Refresh failed, clear tokens
                        tokenStorage.clearTokens()
                    }
                } catch (e: Exception) {
                    // Refresh failed, clear tokens
                    tokenStorage.clearTokens()
                }
            } else {
                // No refresh token, clear everything
                tokenStorage.clearTokens()
            }
        }
        
        return handleResponse(response)
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
        // Use automatic token refresh for protected endpoints
        return makeAuthenticatedRequest {
            client.patch("$baseUrl/change-password") {
                contentType(ContentType.Application.Json)
                addAuthHeader()
                setBody(request)
            }
        }
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        val response = client.post("$baseUrl/refresh-token") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return handleResponse(response)
    }

    override suspend fun logout(request: LogoutRequest): MessageResponse {
        // Use automatic token refresh for protected endpoints
        return makeAuthenticatedRequest {
            client.post("$baseUrl/logout") {
                contentType(ContentType.Application.Json)
                addAuthHeader()
                setBody(request)
            }
        }
    }

    override suspend fun logoutAll(): MessageResponse {
        // Use automatic token refresh for protected endpoints
        return makeAuthenticatedRequest {
            client.post("$baseUrl/logout-all") {
                contentType(ContentType.Application.Json)
                addAuthHeader()
            }
        }
    }
}
