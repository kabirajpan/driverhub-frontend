package com.driverhub.shared.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.driverhub.shared.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    
    fun onEmailPhoneChanged(value: String) {
        _uiState.update { it.copy(emailPhone = value, error = null) }
    }
    
    fun onNameChanged(value: String) {
        _uiState.update { it.copy(name = value, error = null) }
    }
    
    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value, error = null) }
    }
    
    fun onConfirmPasswordChanged(value: String) {
        _uiState.update { it.copy(confirmPassword = value, error = null) }
    }
    
    fun onRoleSelected(role: String) {
        _uiState.update { it.copy(selectedRole = role, error = null) }
    }
    
    fun register() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val currentState = _uiState.value
            
            val result = registerUseCase(
                emailPhone = currentState.emailPhone,
                password = currentState.password,
                confirmPassword = currentState.confirmPassword,
                role = currentState.selectedRole ?: "",
                name = currentState.name
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
                        error = error.message ?: "Registration failed"
                    )
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun resetState() {
        _uiState.value = RegisterUiState()
    }
}
