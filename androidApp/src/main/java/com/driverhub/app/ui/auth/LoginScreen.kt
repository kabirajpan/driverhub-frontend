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

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "example@driverhub.com"
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
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "••••••••"
            )

            Spacer(modifier = Modifier.height(AppSpacing.Medium))

            // Forgot Password
            Text(
                text = "Forgot Password?",
                fontSize = AppFontSize.Body,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                color = PrimaryBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = AppSpacing.ExtraSmall)
                    .clickable { onForgotPasswordClick() },
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            // Log In Button
            PrimaryButton(
                text = "Log In",
                onClick = onLoginClick,
                icon = Icons.Default.ArrowForward
            )

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge2))

            // Divider with "Or continue with"
            SocialLoginDivider(text = "Or continue with")

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            // Social Login Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
            ) {
                GoogleButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )

                FacebookButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
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
                    color = AccentOrange,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }
    }
}
