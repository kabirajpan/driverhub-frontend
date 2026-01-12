package com.driverhub.shared.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val name: String,
    val email: String?,
    val phone: String?,
    val role: UserRole,
    @SerialName("auth_provider")
    val authProvider: AuthProvider,
    @SerialName("is_verified")
    val isVerified: Boolean,
    @SerialName("profile_picture")
    val profilePicture: String? = null,
    val city: String? = null
)

@Serializable
enum class UserRole {
    CAR_OWNER,
    DRIVER
}

@Serializable
enum class AuthProvider {
    EMAIL,
    PHONE,
    GOOGLE,
    FACEBOOK
}
