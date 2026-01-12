package com.driverhub.shared.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.driverhub.shared.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun onEmailPhoneChanged(value: String) {
        _uiState.update { it.copy(emailPhone = value, error = null) }
    }
    
    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }
    
    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = loginUseCase(
                emailPhone = _uiState.value.emailPhone,
                password = _uiState.value.password
            )
            
            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        user = response.user
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Login failed"
                    )
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun resetState() {
        _uiState.value = LoginUiState()
    }
}
