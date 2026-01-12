package com.driverhub.app.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.driverhub.app.ui.theme.*
import com.driverhub.app.ui.common.*
import com.driverhub.shared.presentation.auth.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Reset ViewModel state when screen appears to prevent using cached success state
    DisposableEffect(Unit) {
        viewModel.resetState()
        onDispose { }
    }
    
    // Navigate on success - use flag to prevent duplicate navigation
    var hasNavigated by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.isSuccess, uiState.user) {
        if (uiState.isSuccess && uiState.user != null && !hasNavigated) {
            hasNavigated = true
            onLoginSuccess(uiState.user!!.role.name)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppSpacing.ExtraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge5))

            // App Icon
            IconContainer(
                icon = Icons.Default.LocalTaxi,
                containerColor = PrimaryBlue,
                iconTint = TextWhite,
                containerSize = AppSizes.AppIconMedium,
                iconSize = AppSizes.IconExtraLarge
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            Text(
                text = "Driver Hub",
                fontSize = AppFontSize.Display,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            Text(
                text = "Log in to manage your jobs and earnings.",
                fontSize = AppFontSize.BodyLarge,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge3))

            // Email or Phone Label
            Text(
                text = "Email or Phone",
                fontSize = AppFontSize.BodyLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            // Email Input Field
            EmailTextField(
                value = uiState.emailPhone,
                onValueChange = viewModel::onEmailPhoneChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "example@driverhub.com",
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            // Password Label
            Text(
                text = "Password",
                fontSize = AppFontSize.BodyLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            // Password Input Field
            PasswordTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "••••••••",
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(AppSpacing.Medium))

            // Forgot Password
            Text(
                text = "Forgot Password?",
                fontSize = AppFontSize.Body,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                color = if (uiState.isLoading) TextTertiary else PrimaryBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = AppSpacing.ExtraSmall)
                    .clickable(enabled = !uiState.isLoading) {
                        onForgotPasswordClick()
                    },
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            // Error Message
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    fontSize = AppFontSize.Body,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppSpacing.Default))
            }

            // Log In Button
            PrimaryButton(
                text = "Log In",
                onClick = viewModel::login,
                icon = Icons.Default.ArrowForward,
                isLoading = uiState.isLoading,
                enabled = uiState.emailPhone.isNotBlank() &&
                         uiState.password.isNotBlank()
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

            // Divider with "Or continue with"
            SocialLoginDivider(text = "Or continue with")

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            // Social Login Buttons (disabled during loading)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
            ) {
                GoogleButton(
                    onClick = { /* TODO: Google login */ },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                )
                FacebookButton(
                    onClick = { /* TODO: Facebook login */ },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Sign Up Text
            Row(
                modifier = Modifier.padding(bottom = AppSpacing.ExtraLarge2),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = AppFontSize.Body,
                    color = TextSecondary
                )
                Text(
                    text = "Sign Up",
                    fontSize = AppFontSize.Body,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                    color = if (uiState.isLoading) TextTertiary else AccentOrange,
                    modifier = Modifier.clickable(enabled = !uiState.isLoading) {
                        onSignUpClick()
                    }
                )
            }
        }
    }
}
