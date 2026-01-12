package com.driverhub.shared.presentation.auth.forgotpassword

enum class ForgotPasswordStep {
    ENTER_EMAIL_PHONE,
    ACCOUNT_FOUND,
    VERIFY_OTP,
    RESET_PASSWORD,
    PASSWORD_UPDATED  // New step for success screen
}

data class ForgotPasswordUiState(
    val emailPhone: String = "",
    val otpCode: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val step: ForgotPasswordStep = ForgotPasswordStep.ENTER_EMAIL_PHONE,
    val maskedIdentifier: String? = null,
    val availableMethods: List<String> = emptyList(),
    val selectedVerificationMethod: String? = null, // "EMAIL" or "SMS"
    val isLoading: Boolean = false,
    val error: String? = null
)
