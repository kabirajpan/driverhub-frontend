package com.driverhub.shared.presentation.auth.register

import com.driverhub.shared.domain.model.User

data class RegisterUiState(
    val emailPhone: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val selectedRole: String? = null, // "CAR_OWNER" or "DRIVER"
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: User? = null
)
