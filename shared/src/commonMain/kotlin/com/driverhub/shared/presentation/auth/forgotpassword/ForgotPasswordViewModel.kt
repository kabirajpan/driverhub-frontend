package com.driverhub.shared.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.driverhub.shared.domain.usecase.auth.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()
    
    fun onEmailPhoneChanged(value: String) {
        _uiState.update { it.copy(emailPhone = value, error = null) }
    }
    
    fun onOtpChanged(value: String) {
        _uiState.update { it.copy(otpCode = value, error = null) }
    }
    
    fun onNewPasswordChanged(value: String) {
        _uiState.update { it.copy(newPassword = value, error = null) }
    }
    
    fun onConfirmPasswordChanged(value: String) {
        _uiState.update { it.copy(confirmPassword = value, error = null) }
    }
    
    fun onVerificationMethodSelected(method: String) {
        _uiState.update { it.copy(selectedVerificationMethod = method) }
    }
    
    fun checkAccount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = forgotPasswordUseCase.checkAccount(_uiState.value.emailPhone)
            
            result.onSuccess { response ->
                if (response.exists) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            step = ForgotPasswordStep.ACCOUNT_FOUND,
                            maskedIdentifier = response.maskedIdentifier,
                            availableMethods = response.availableMethods
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "No account found with this email/phone"
                        )
                    }
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to check account"
                    )
                }
            }
        }
    }
    
    fun sendOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = forgotPasswordUseCase.sendOtp(_uiState.value.emailPhone)
            
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        step = ForgotPasswordStep.VERIFY_OTP
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to send OTP"
                    )
                }
            }
        }
    }
    
    fun verifyOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = forgotPasswordUseCase.verifyOtp(
                emailPhone = _uiState.value.emailPhone,
                otpCode = _uiState.value.otpCode
            )
            
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        step = ForgotPasswordStep.RESET_PASSWORD
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Invalid OTP"
                    )
                }
            }
        }
    }
    
    fun resetPassword() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            val result = forgotPasswordUseCase.resetPassword(
                emailPhone = _uiState.value.emailPhone,
                otpCode = _uiState.value.otpCode,
                newPassword = _uiState.value.newPassword,
                confirmPassword = _uiState.value.confirmPassword
            )
            
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        step = ForgotPasswordStep.PASSWORD_UPDATED  // Changed: Go to success screen
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to reset password"
                    )
                }
            }
        }
    }
    
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    fun resetState() {
        _uiState.value = ForgotPasswordUiState()
    }
}
