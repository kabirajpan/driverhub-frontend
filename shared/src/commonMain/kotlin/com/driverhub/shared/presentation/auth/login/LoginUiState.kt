package com.driverhub.shared.presentation.auth.login

import com.driverhub.shared.domain.model.User

data class LoginUiState(
    val emailPhone: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val user: User? = null
)
