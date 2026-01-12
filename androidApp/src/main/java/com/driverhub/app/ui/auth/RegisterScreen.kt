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
import com.driverhub.app.ui.theme.*
import com.driverhub.app.ui.common.*
import com.driverhub.shared.presentation.auth.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterComplete: () -> Unit = {},
    viewModel: RegisterViewModel = koinViewModel()
) {
    var currentStep by remember { mutableStateOf(1) }
    var agreedToTerms by remember { mutableStateOf(false) }
    
    val uiState by viewModel.uiState.collectAsState()
    
    // Navigate on success
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterComplete()
        }
    }

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
                    IconButton(
                        onClick = {
                            if (currentStep > 1) {
                                currentStep--
                            } else {
                                onBackClick()
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
                    1 -> EmailStepContent(
                        email = uiState.emailPhone,
                        onEmailChange = viewModel::onEmailPhoneChanged,
                        onNextClick = { currentStep = 2 },
                        onLoginClick = onLoginClick,
                        currentStep = currentStep,
                        isLoading = uiState.isLoading
                    )
                    2 -> RoleSelectionContent(
                        selectedRole = uiState.selectedRole,
                        onRoleSelected = viewModel::onRoleSelected,
                        onContinueClick = { currentStep = 3 },
                        currentStep = currentStep,
                        isLoading = uiState.isLoading
                    )
                    3 -> AccountSetupContent(
                        fullName = uiState.name,
                        onFullNameChange = viewModel::onNameChanged,
                        password = uiState.password,
                        onPasswordChange = viewModel::onPasswordChanged,
                        confirmPassword = uiState.confirmPassword,
                        onConfirmPasswordChange = viewModel::onConfirmPasswordChanged,
                        agreedToTerms = agreedToTerms,
                        onAgreedToTermsChange = { agreedToTerms = it },
                        onCreateAccountClick = viewModel::register,
                        currentStep = currentStep,
                        isLoading = uiState.isLoading,
                        error = uiState.error
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

        EmailTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email or Phone Number",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

        SocialLoginDivider()

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        SocialLoginRow(
            onGoogleClick = { /* TODO: Google registration */ },
            onSecondaryClick = { /* TODO: Facebook registration */ },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PrimaryButton(
            text = "Next Step",
            onClick = onNextClick,
            icon = Icons.Default.ArrowForward,
            enabled = email.isNotEmpty() && !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

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
                color = if (isLoading) TextTertiary else PrimaryBlue,
                modifier = Modifier.clickable(enabled = !isLoading) { onLoginClick() }
            )
        }
    }
}

@Composable
private fun RoleSelectionContent(
    selectedRole: String?,
    onRoleSelected: (String) -> Unit,
    onContinueClick: () -> Unit,
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

        RoleCard(
            icon = Icons.Default.DirectionsCar,
            iconColor = PrimaryBlue,
            title = "I am a Driver",
            subtitle = "Looking for driving jobs and opportunities.",
            isSelected = selectedRole == "DRIVER",
            onClick = { onRoleSelected("DRIVER") },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        RoleCard(
            icon = Icons.Default.Key,
            iconColor = AccentOrange,
            title = "I am a Car Owner",
            subtitle = "Looking to hire professional drivers.",
            isSelected = selectedRole == "CAR_OWNER",
            onClick = { onRoleSelected("CAR_OWNER") },
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.weight(1f))

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PrimaryButton(
            text = "Continue",
            onClick = onContinueClick,
            enabled = selectedRole != null && !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))
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

        AppTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "John Doe",
            leadingIcon = Icons.Default.Person,
            trailingIcon = if (fullName.isNotEmpty()) Icons.Default.CheckCircle else null,
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PasswordTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "••••••••",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        PasswordTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "••••••••",
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(AppSpacing.Large))

        // Error Message
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

        // Terms Checkbox
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = onAgreedToTermsChange,
                enabled = !isLoading,
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
                        color = if (isLoading) TextTertiary else PrimaryBlue
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
                        color = if (isLoading) TextTertiary else PrimaryBlue
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

        StepIndicator(currentStep = currentStep)

        Spacer(modifier = Modifier.height(AppSpacing.Default))

        SecondaryButton(
            text = "Create Account",
            onClick = onCreateAccountClick,
            enabled = fullName.isNotEmpty() && 
                     password.isNotEmpty() && 
                     confirmPassword.isNotEmpty() && 
                     agreedToTerms && 
                     !isLoading,
            isLoading = isLoading
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.Default))
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))
    }
}
