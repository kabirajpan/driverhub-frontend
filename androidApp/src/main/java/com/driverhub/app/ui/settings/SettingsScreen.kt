package com.driverhub.app.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.driverhub.app.ui.theme.*
import com.driverhub.app.ui.common.*
import com.driverhub.shared.presentation.settings.ChangePasswordViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ChangePasswordViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Show success dialog when password changed
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            showChangePasswordDialog = false
            showSuccessDialog = true
            viewModel.resetSuccessState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = AppFontSize.Heading,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Account Section
            SettingsSection(title = "Account") {
                SettingsItem(
                    icon = Icons.Default.Lock,
                    title = "Change Password",
                    subtitle = "Update your account password",
                    onClick = { showChangePasswordDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            // Preferences Section
            SettingsSection(title = "Preferences") {
                SettingsItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Manage notification preferences",
                    onClick = { /* TODO */ }
                )
                Divider(color = BorderLight)
                SettingsItem(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = "English",
                    onClick = { /* TODO */ }
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            // About Section
            SettingsSection(title = "About") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "Privacy Policy",
                    subtitle = "Read our privacy policy",
                    onClick = { /* TODO */ }
                )
                Divider(color = BorderLight)
                SettingsItem(
                    icon = Icons.Default.Description,
                    title = "Terms of Service",
                    subtitle = "Read our terms of service",
                    onClick = { /* TODO */ }
                )
                Divider(color = BorderLight)
                SettingsItem(
                    icon = Icons.Default.Info,
                    title = "App Version",
                    subtitle = "1.0.0",
                    onClick = null
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            // Logout Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.Default)
            ) {
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        width = AppSpacing.ExtraSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(AppSizes.IconSmall)
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.Small))
                    Text(
                        text = "Logout",
                        fontSize = AppFontSize.BodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge3))
        }
    }

    // Change Password Dialog
    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            uiState = uiState,
            onDismiss = {
                showChangePasswordDialog = false
                viewModel.resetState()
            },
            onCurrentPasswordChanged = viewModel::onCurrentPasswordChanged,
            onNewPasswordChanged = viewModel::onNewPasswordChanged,
            onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
            onChangePassword = viewModel::changePassword
        )
    }

    // Success Dialog
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(AppSizes.IconLarge)
                )
            },
            title = {
                Text(
                    text = "Success!",
                    fontSize = AppFontSize.Heading,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = "Your password has been changed successfully.",
                    fontSize = AppFontSize.Body,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text("OK", fontSize = AppFontSize.BodyLarge)
                }
            },
            containerColor = CardWhite
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = AppFontSize.Body,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            modifier = Modifier.padding(
                horizontal = AppSpacing.Default,
                vertical = AppSpacing.Small
            )
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.cardElevation(defaultElevation = AppSpacing.ExtraSmall)
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)?
) {
    val modifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(AppSpacing.Default)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(AppSpacing.Default)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(AppSizes.IconMedium)
        )
        Spacer(modifier = Modifier.width(AppSpacing.Default))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Text(
                text = subtitle,
                fontSize = AppFontSize.Body,
                color = TextSecondary
            )
        }
        if (onClick != null) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(AppSizes.IconSmall)
            )
        }
    }
}

@Composable
fun ChangePasswordDialog(
    uiState: com.driverhub.shared.presentation.settings.ChangePasswordUiState,
    onDismiss: () -> Unit,
    onCurrentPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onChangePassword: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Change Password",
                fontSize = AppFontSize.Heading,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.Default)
            ) {
                // Current Password
                Text(
                    text = "Current Password",
                    fontSize = AppFontSize.Body,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                PasswordTextField(
                    value = uiState.currentPassword,
                    onValueChange = onCurrentPasswordChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Enter current password",
                    enabled = !uiState.isLoading
                )

                // New Password
                Text(
                    text = "New Password",
                    fontSize = AppFontSize.Body,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                PasswordTextField(
                    value = uiState.newPassword,
                    onValueChange = onNewPasswordChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Enter new password",
                    enabled = !uiState.isLoading
                )

                // Confirm New Password
                Text(
                    text = "Confirm New Password",
                    fontSize = AppFontSize.Body,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                PasswordTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Confirm new password",
                    enabled = !uiState.isLoading
                )

                // Error Message
                uiState.error?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        fontSize = AppFontSize.Body,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onChangePassword,
                enabled = !uiState.isLoading &&
                        uiState.currentPassword.isNotBlank() &&
                        uiState.newPassword.isNotBlank() &&
                        uiState.confirmPassword.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(AppSizes.IconSmall),
                        color = TextWhite,
                        strokeWidth = AppSpacing.ExtraSmall
                    )
                } else {
                    Text("Change Password")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !uiState.isLoading
            ) {
                Text("Cancel")
            }
        },
        containerColor = CardWhite
    )
}
