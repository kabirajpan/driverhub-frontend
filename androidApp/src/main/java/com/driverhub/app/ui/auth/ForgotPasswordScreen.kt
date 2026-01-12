package com.driverhub.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.background
import kotlinx.coroutines.delay
import com.driverhub.app.ui.theme.*
import com.driverhub.app.ui.common.*
import com.driverhub.shared.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.driverhub.shared.presentation.auth.forgotpassword.ForgotPasswordStep
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPasswordResetComplete: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = koinViewModel()
) {
    var selectedTab by remember { mutableStateOf("Email") }
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Map ViewModel steps to UI steps (1-5)
    val currentStep = when (uiState.step) {
        ForgotPasswordStep.ENTER_EMAIL_PHONE -> 1
        ForgotPasswordStep.ACCOUNT_FOUND -> 2
        ForgotPasswordStep.VERIFY_OTP -> 3
        ForgotPasswordStep.RESET_PASSWORD -> 4
        ForgotPasswordStep.PASSWORD_UPDATED -> 5
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar - Hide on success screen
            if (currentStep != 5) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Forgot Password",
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                when (uiState.step) {
                                    ForgotPasswordStep.ENTER_EMAIL_PHONE -> onBackClick()
                                    ForgotPasswordStep.ACCOUNT_FOUND -> viewModel.resetState()
                                    ForgotPasswordStep.VERIFY_OTP -> viewModel.resetState()
                                    ForgotPasswordStep.RESET_PASSWORD -> viewModel.resetState()
                                    ForgotPasswordStep.PASSWORD_UPDATED -> onLoginClick()
                                }
                            },
                            enabled = !uiState.isLoading
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = if (uiState.isLoading) TextTertiary else TextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )
            }

            // Animated Content
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    }
                },
                label = "step_animation"
            ) { step ->
                when (step) {
                    1 -> RecoverAccountContent(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        emailOrPhone = uiState.emailPhone,
                        onEmailOrPhoneChange = viewModel::onEmailPhoneChanged,
                        onSendResetLink = viewModel::checkAccount,
                        onLoginClick = onLoginClick,
                        currentStep = currentStep,
                        isLoading = uiState.isLoading,
                        error = uiState.error
                    )
                    2 -> VerificationMethodContent(
                        selectedMethod = uiState.selectedVerificationMethod ?: "email",
                        onMethodSelected = viewModel::onVerificationMethodSelected,
                        email = uiState.maskedIdentifier ?: "d••••r@example.com",
                        phone = uiState.maskedIdentifier ?: "+1 ••• ••• 4921",
                        onSendCode = viewModel::sendOtp,
                        onTryAnotherAccount = { viewModel.resetState() },
                        currentStep = currentStep,
                        isLoading = uiState.isLoading
                    )
                    3 -> OTPVerificationContent(
                        otp = uiState.otpCode,
                        onOtpChange = viewModel::onOtpChanged,
                        phoneNumber = uiState.emailPhone,
                        onVerifyClick = viewModel::verifyOtp,
                        onResendCode = viewModel::sendOtp,
                        onEditPhone = { viewModel.resetState() },
                        currentStep = currentStep,
                        isLoading = uiState.isLoading,
                        error = uiState.error
                    )
                    4 -> CreateNewPasswordContent(
                        newPassword = uiState.newPassword,
                        onNewPasswordChange = viewModel::onNewPasswordChanged,
                        confirmPassword = uiState.confirmPassword,
                        onConfirmPasswordChange = viewModel::onConfirmPasswordChanged,
                        onResetPassword = viewModel::resetPassword,
                        currentStep = currentStep,
                        isLoading = uiState.isLoading,
                        error = uiState.error
                    )
                    5 -> PasswordUpdatedContent(
                        onBackToLogin = onLoginClick
                    )
                }
            }
        }
    }
}

@Composable
private fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppSpacing.ExtraLarge, vertical = AppSpacing.Default),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .width(if (index + 1 == currentStep) AppSpacing.ExtraLarge2 else AppSpacing.Small)
                    .height(AppSpacing.Small)
                    .clip(AppShapes.ProgressBar)
                    .background(
                        if (index + 1 <= currentStep) PrimaryBlue
                        else BorderMedium
                    )
            )
            if (index < 3) {
                Spacer(modifier = Modifier.width(AppSpacing.Small))
            }
        }
    }
}

@Composable
private fun RecoverAccountContent(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    emailOrPhone: String,
    onEmailOrPhoneChange: (String) -> Unit,
    onSendResetLink: () -> Unit,
    onLoginClick: () -> Unit,
    currentStep: Int,
    isLoading: Boolean,
    error: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        IconContainer(
            icon = Icons.Default.LockReset,
            containerColor = BlueTint,
            iconTint = PrimaryBlue,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Recover Account",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "Don't worry! It happens. Please enter the address\nassociated with your account.",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = AppFontSize.Title
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        TabSelector(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            tab1 = "Email",
            tab2 = "Phone Number"
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = if (selectedTab == "Email") "Email Address" else "Phone Number",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        if (selectedTab == "Email") {
            EmailTextField(
                value = emailOrPhone,
                onValueChange = onEmailOrPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "driver@example.com",
                enabled = !isLoading
            )
        } else {
            PhoneTextField(
                value = emailOrPhone,
                onValueChange = onEmailOrPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "+91 12345 67890",
                enabled = !isLoading
            )
        }

        if (error != null) {
            Spacer(modifier = Modifier.height(AppSpacing.Default))
            Text(
                text = error,
                fontSize = AppFontSize.Body,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PrimaryButton(
            text = "Send Reset Link",
            onClick = onSendResetLink,
            icon = Icons.Default.ArrowForward,
            enabled = emailOrPhone.isNotEmpty() && !isLoading,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        Row(
            modifier = Modifier.padding(bottom = AppSpacing.ExtraLarge2),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Remember password? ",
                fontSize = AppFontSize.Body,
                color = TextSecondary
            )
            Text(
                text = "Log in",
                fontSize = AppFontSize.Body,
                fontWeight = FontWeight.SemiBold,
                color = if (isLoading) TextTertiary else PrimaryBlue,
                modifier = Modifier.clickable(enabled = !isLoading) { onLoginClick() }
            )
        }
    }
}

@Composable
private fun VerificationMethodContent(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit,
    email: String,
    phone: String,
    onSendCode: () -> Unit,
    onTryAnotherAccount: () -> Unit,
    currentStep: Int,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        IconContainer(
            icon = Icons.Default.MarkEmailRead,
            containerColor = OrangeTint,
            iconTint = AccentOrange,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Verification Method",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "We found your account! Select how you would like\nto receive your recovery code.",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = AppFontSize.Title
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        SelectionCard(
            icon = Icons.Default.Email,
            title = "Send via Email",
            subtitle = email,
            isSelected = selectedMethod == "email",
            onClick = { onMethodSelected("email") },
            iconBackgroundColor = PrimaryBlue,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        SelectionCard(
            icon = Icons.Default.Message,
            title = "Send via SMS",
            subtitle = phone,
            isSelected = selectedMethod == "sms",
            onClick = { onMethodSelected("sms") },
            iconBackgroundColor = TextSecondary,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        SecondaryButton(
            text = "Send Code",
            onClick = onSendCode,
            icon = Icons.Default.ArrowForward,
            enabled = !isLoading,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))
    }
}

@Composable
private fun OTPVerificationContent(
    otp: String,
    onOtpChange: (String) -> Unit,
    phoneNumber: String,
    onVerifyClick: () -> Unit,
    onResendCode: () -> Unit,
    onEditPhone: () -> Unit,
    currentStep: Int,
    isLoading: Boolean,
    error: String?
) {
    var timeLeft by remember { mutableStateOf(45) }
    var canResend by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else {
            canResend = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(AppSizes.AppIconMedium + AppSpacing.ExtraLarge + AppSizes.IconMedium),
                shape = CircleShape,
                color = GreenTint.copy(alpha = 0.1f)
            ) {}
            
            IconContainer(
                icon = Icons.Default.LockOpen,
                containerColor = GreenTint,
                iconTint = SuccessGreen,
                containerSize = AppSizes.AppIconMedium,
                iconSize = AppSizes.IconExtraLarge,
                elevation = AppElevation.None,
                shape = CircleShape
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Verification Code",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "We sent a 6-digit code to ",
                fontSize = AppFontSize.Body,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = phoneNumber,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = "Edit",
                fontSize = AppFontSize.Body,
                fontWeight = FontWeight.SemiBold,
                color = if (isLoading) TextTertiary else PrimaryBlue,
                modifier = Modifier.clickable(enabled = !isLoading) { onEditPhone() }
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        OTPInputField(
            otp = otp,
            onOtpChange = onOtpChange,
            digitCount = 6,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        if (error != null) {
            Text(
                text = error,
                fontSize = AppFontSize.Body,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppSpacing.Default))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Timer,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(AppSizes.IconMedium)
            )
            Spacer(modifier = Modifier.width(AppSpacing.ExtraSmall))
            Text(
                text = "00:${timeLeft.toString().padStart(2, '0')}",
                fontSize = AppFontSize.BodyLarge,
                color = TextTertiary
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Didn't receive the code? ",
                fontSize = AppFontSize.Body,
                color = TextSecondary
            )
            Text(
                text = "Resend Code",
                fontSize = AppFontSize.Body,
                fontWeight = FontWeight.SemiBold,
                color = if (canResend && !isLoading) AccentOrange else TextTertiary,
                modifier = Modifier.clickable(enabled = canResend && !isLoading) { 
                    if (canResend && !isLoading) {
                        onResendCode()
                        timeLeft = 45
                        canResend = false
                    }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PrimaryButton(
            text = "Verify & Proceed",
            onClick = onVerifyClick,
            icon = Icons.Default.ArrowForward,
            enabled = otp.length == 6 && !isLoading,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))
    }
}

@Composable
private fun CreateNewPasswordContent(
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onResetPassword: () -> Unit,
    currentStep: Int,
    isLoading: Boolean,
    error: String?
) {
    val hasMinLength = newPassword.length >= 8
    val hasUpperCase = newPassword.any { it.isUpperCase() }
    val hasLowerCase = newPassword.any { it.isLowerCase() }
    val hasNumber = newPassword.any { it.isDigit() }
    val hasSpecialChar = newPassword.any { !it.isLetterOrDigit() }
    val passwordsMatch = newPassword.isNotEmpty() && newPassword == confirmPassword
    
    val isPasswordValid = hasMinLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar && passwordsMatch

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        IconContainer(
            icon = Icons.Default.Lock,
            containerColor = BlueTint,
            iconTint = PrimaryBlue,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Create New Password",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "Your new password must be different from\npreviously used passwords.",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = AppFontSize.Title
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        Text(
            text = "New Password",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        PasswordTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Enter new password",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        Text(
            text = "Confirm Password",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Re-enter password",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        if (error != null) {
            Text(
                text = error,
                fontSize = AppFontSize.Body,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(AppSpacing.Default))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(AppSizes.IconSmall)
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = "Use 8+ characters with a mix of uppercase, lowercase, numbers & symbols for a strong password.",
                fontSize = AppFontSize.Small,
                color = TextTertiary,
                lineHeight = AppFontSize.Body
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PrimaryButton(
            text = "Reset Password",
            onClick = onResetPassword,
            icon = Icons.Default.Check,
            enabled = isPasswordValid && !isLoading,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
    }
}

@Composable
private fun PasswordUpdatedContent(
    onBackToLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        // Success Icon with Circles
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Outer circle
            Surface(
                modifier = Modifier.size(AppSizes.AppIconMedium + AppSpacing.ExtraLarge + AppSizes.IconMedium),
                shape = CircleShape,
                color = GreenTint.copy(alpha = 0.1f)
            ) {}
            
            // Icon Container
            IconContainer(
                icon = Icons.Default.Check,
                containerColor = SuccessGreen,
                iconTint = TextWhite,
                containerSize = AppSizes.AppIconMedium,
                iconSize = AppSizes.IconExtraLarge,
                elevation = AppElevation.Level2,
                shape = CircleShape
            )
        }
        
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
        
        // Title
        Text(
            text = "Password Updated!",
            fontSize = AppFontSize.Display,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.Default))
        
        // Description
        Text(
            text = "Your password has been changed successfully. You can now use your new password to log in to Driver Hub.",
            fontSize = AppFontSize.BodyLarge,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = AppFontSize.Title,
            modifier = Modifier.padding(horizontal = AppSpacing.Default)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Back to Login Button
        PrimaryButton(
            text = "Back to Login",
            onClick = onBackToLogin
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.Default))
        
        // Need help text
        Text(
            text = "Need help logging in?",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
    }
}
