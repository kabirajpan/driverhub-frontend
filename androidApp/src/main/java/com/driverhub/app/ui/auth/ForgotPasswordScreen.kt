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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPasswordResetComplete: () -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(1) }
    var selectedTab by remember { mutableStateOf("Email") }
    var emailOrPhone by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf("email") }
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
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
                    IconButton(onClick = {
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            onBackClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )

            // Animated Content
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        // Forward: slide in from right, slide out to left
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    } else {
                        // Backward: slide in from left, slide out to right
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
                        emailOrPhone = emailOrPhone,
                        onEmailOrPhoneChange = { emailOrPhone = it },
                        onSendResetLink = { currentStep = 2 },
                        onLoginClick = onLoginClick,
                        currentStep = currentStep
                    )
                    2 -> VerificationMethodContent(
                        selectedMethod = selectedMethod,
                        onMethodSelected = { selectedMethod = it },
                        email = if (selectedTab == "Email") emailOrPhone else "d••••r@example.com",
                        phone = if (selectedTab == "Phone Number") emailOrPhone else "+1 ••• ••• 4921",
                        onSendCode = { currentStep = 3 },
                        onTryAnotherAccount = { currentStep = 1 },
                        currentStep = currentStep
                    )
                    3 -> OTPVerificationContent(
                        otp = otp,
                        onOtpChange = { otp = it },
                        phoneNumber = emailOrPhone,
                        onVerifyClick = { currentStep = 4 },
                        onResendCode = { /* Handle resend */ },
                        onEditPhone = { currentStep = 1 },
                        currentStep = currentStep
                    )
                    4 -> CreateNewPasswordContent(
                        newPassword = newPassword,
                        onNewPasswordChange = { newPassword = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        onResetPassword = onPasswordResetComplete,
                        currentStep = currentStep
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
    currentStep: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        // Icon
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

        // Email/Phone Tab Selector
        TabSelector(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            tab1 = "Email",
            tab2 = "Phone Number"
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        // Label
        Text(
            text = if (selectedTab == "Email") "Email Address" else "Phone Number",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        // Input Field
        if (selectedTab == "Email") {
            EmailTextField(
                value = emailOrPhone,
                onValueChange = onEmailOrPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "driver@example.com"
            )
        } else {
            PhoneTextField(
                value = emailOrPhone,
                onValueChange = onEmailOrPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "+91 12345 67890"
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Step Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Send Reset Link Button
        PrimaryButton(
            text = "Send Reset Link",
            onClick = onSendResetLink,
            icon = Icons.Default.ArrowForward,
            enabled = emailOrPhone.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // Remember password? Log in
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
                color = PrimaryBlue,
                modifier = Modifier.clickable { onLoginClick() }
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
    currentStep: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppSpacing.ExtraLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        // Icon
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

        // Email Option
        SelectionCard(
            icon = Icons.Default.Email,
            title = "Send via Email",
            subtitle = email,
            isSelected = selectedMethod == "email",
            onClick = { onMethodSelected("email") },
            iconBackgroundColor = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // SMS Option
        SelectionCard(
            icon = Icons.Default.Message,
            title = "Send via SMS",
            subtitle = phone,
            isSelected = selectedMethod == "sms",
            onClick = { onMethodSelected("sms") },
            iconBackgroundColor = TextSecondary
        )

        Spacer(modifier = Modifier.weight(1f))

        // Step Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Send Code Button
        SecondaryButton(
            text = "Send Code",
            onClick = onSendCode,
            icon = Icons.Default.ArrowForward
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Try another account
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
    currentStep: Int
) {
    var timeLeft by remember { mutableStateOf(45) }
    var canResend by remember { mutableStateOf(false) }

    // Timer countdown
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

        // Lock Icon with Circle
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

        // Phone number with Edit
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
                color = PrimaryBlue,
                modifier = Modifier.clickable { onEditPhone() }
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // OTP Input (6 digits)
        OTPInputField(
            otp = otp,
            onOtpChange = onOtpChange,
            digitCount = 6
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // Timer
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

        // Resend Code
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
                color = if (canResend) AccentOrange else TextTertiary,
                modifier = Modifier.clickable(enabled = canResend) { 
                    if (canResend) {
                        onResendCode()
                        timeLeft = 45
                        canResend = false
                    }
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Step Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Verify Button
        PrimaryButton(
            text = "Verify & Proceed",
            onClick = onVerifyClick,
            icon = Icons.Default.ArrowForward,
            enabled = otp.length == 6
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
    currentStep: Int
) {
    // Password validation states
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

        // Icon
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

        // New Password Label
        Text(
            text = "New Password",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        // New Password Field
        PasswordTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Enter new password"
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // Confirm Password Label
        Text(
            text = "Confirm Password",
            fontSize = AppFontSize.BodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        // Confirm Password Field
        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Re-enter password"
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        // Password hint message
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

        // Step Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Reset Password Button
        PrimaryButton(
            text = "Reset Password",
            onClick = onResetPassword,
            icon = Icons.Default.Check,
            enabled = isPasswordValid
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))
    }
}

@Composable
private fun PasswordRequirement(
    text: String,
    isMet: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = AppSpacing.ExtraSmall)
    ) {
        Icon(
            imageVector = if (isMet) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isMet) SuccessGreen else TextTertiary,
            modifier = Modifier.size(AppSizes.IconSmall)
        )
        Spacer(modifier = Modifier.width(AppSpacing.Small))
        Text(
            text = text,
            fontSize = AppFontSize.Body,
            color = if (isMet) TextPrimary else TextSecondary
        )
    }
}
