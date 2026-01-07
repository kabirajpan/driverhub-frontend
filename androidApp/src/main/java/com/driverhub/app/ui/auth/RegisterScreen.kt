package com.driverhub.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.driverhub.app.ui.theme.*
import com.driverhub.app.ui.common.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterComplete: () -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(1) }
    var email by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) }

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
                        text = "Register",
                        fontSize = AppFontSize.Title,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
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
                    1 -> EmailStepContent(
                        email = email,
                        onEmailChange = { email = it },
                        onNextClick = { currentStep = 2 },
                        onLoginClick = onLoginClick,
                        currentStep = currentStep
                    )
                    2 -> RoleSelectionContent(
                        selectedRole = selectedRole,
                        onRoleSelected = { selectedRole = it },
                        onContinueClick = { currentStep = 3 },
                        currentStep = currentStep
                    )
                    3 -> AccountSetupContent(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        password = password,
                        onPasswordChange = { password = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        agreedToTerms = agreedToTerms,
                        onAgreedToTermsChange = { agreedToTerms = it },
                        onCreateAccountClick = onRegisterComplete,
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
        repeat(3) { index ->
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
            if (index < 2) {
                Spacer(modifier = Modifier.width(AppSpacing.Small))
            }
        }
    }
}

@Composable
private fun EmailStepContent(
    email: String,
    onEmailChange: (String) -> Unit,
    onNextClick: () -> Unit,
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
            icon = Icons.Default.Email,
            containerColor = BlueTint,
            iconTint = PrimaryBlue,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Let's get started",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "Enter your email or phone number to\ncreate your account.",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = AppFontSize.Title
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // Email Input
        EmailTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email or Phone Number"
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // Social Login Divider
        SocialLoginDivider()

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        // Social Buttons
        SocialLoginRow(
            onGoogleClick = { },
            onSecondaryClick = { }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Progress Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Next Button
        PrimaryButton(
            text = "Next Step",
            onClick = onNextClick,
            icon = Icons.Default.ArrowForward,
            enabled = email.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))  // 20dp (was 16dp - just 4dp increase)

// Login Text
Row(
    modifier = Modifier.padding(bottom = AppSpacing.ExtraLarge2), 
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                fontSize = AppFontSize.Body,
                color = TextSecondary
            )
            Text(
                text = "Log in",
                fontSize = AppFontSize.Body,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                color = PrimaryBlue,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}

@Composable
private fun RoleSelectionContent(
    selectedRole: String,
    onRoleSelected: (String) -> Unit,
    onContinueClick: () -> Unit,
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
            icon = Icons.Default.Person,
            containerColor = OrangeTint,
            iconTint = AccentOrange,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Choose your role",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "How will you be using Driver Hub today?",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // Driver Option
        RoleCard(
            icon = Icons.Default.DirectionsCar,
            iconColor = PrimaryBlue,
            title = "I am a Driver",
            subtitle = "Looking for driving jobs and opportunities.",
            isSelected = selectedRole == "driver",
            onClick = { onRoleSelected("driver") }
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Car Owner Option
        RoleCard(
            icon = Icons.Default.Key,
            iconColor = AccentOrange,
            title = "I am a Car Owner",
            subtitle = "Looking to hire professional drivers.",
            isSelected = selectedRole == "owner",
            onClick = { onRoleSelected("owner") }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Progress Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

// Continue Button
PrimaryButton(
    text = "Continue",
    onClick = onContinueClick,
    enabled = selectedRole.isNotEmpty()
)

Spacer(modifier = Modifier.height(AppSpacing.Default))  // 16dp

// Match Step 1's position with larger spacing
Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))  // 60dp
    }
}

@Composable
private fun AccountSetupContent(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    agreedToTerms: Boolean,
    onAgreedToTermsChange: (Boolean) -> Unit,
    onCreateAccountClick: () -> Unit,
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
            icon = Icons.Default.Security,
            containerColor = GreenTint,
            iconTint = SuccessGreen,
            containerSize = AppSizes.AppIconMedium,
            iconSize = AppSizes.IconExtraLarge,
            elevation = AppElevation.None
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Text(
            text = "Secure your account",
            fontSize = AppFontSize.HeadingLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(AppSpacing.Small))

        Text(
            text = "Set up your profile name and password.",
            fontSize = AppFontSize.Body,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        // Full Name
        AppTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "John Doe",
            leadingIcon = Icons.Default.Person,
            trailingIcon = if (fullName.isNotEmpty()) Icons.Default.CheckCircle else null
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Password
        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "••••••••"
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        // Confirm Password
        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "••••••••"
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // Terms Checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = onAgreedToTermsChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = PrimaryBlue
                )
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Column {
                Row {
                    Text(
                        text = "By creating an account, you agree to our ",
                        fontSize = AppFontSize.Small,
                        color = TextSecondary
                    )
                }
                Row {
                    Text(
                        text = "Terms of Service",
                        fontSize = AppFontSize.Small,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        color = PrimaryBlue
                    )
                    Text(
                        text = " and ",
                        fontSize = AppFontSize.Small,
                        color = TextSecondary
                    )
                    Text(
                        text = "Privacy Policy",
                        fontSize = AppFontSize.Small,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                        color = PrimaryBlue
                    )
                    Text(
                        text = ".",
                        fontSize = AppFontSize.Small,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Progress Indicator
        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

// Create Account Button
SecondaryButton(
    text = "Create Account",
    onClick = onCreateAccountClick,
    enabled = fullName.isNotEmpty() && password.isNotEmpty() && 
             confirmPassword.isNotEmpty() && agreedToTerms
)
Spacer(modifier = Modifier.height(AppSpacing.Default))  // 16dp

// Match Step 1's position with larger spacing
Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))  // 60dp
    }
}
