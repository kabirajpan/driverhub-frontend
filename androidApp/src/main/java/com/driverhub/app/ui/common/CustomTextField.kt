package com.driverhub.app.ui.common

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.driverhub.app.ui.theme.*

/**
 * Standard Text Field
 * Used for email, name, phone, etc.
 */
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                style = AppTextStyles.Placeholder
            )
        },
        label = if (label != null) {
            { Text(label, fontSize = AppFontSize.Body) }
        } else null,
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = TextSecondary
                )
            }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    }
                } else {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = SuccessGreen
                    )
                }
            }
        } else null,
        shape = AppShapes.TextField,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceWhite,
            unfocusedContainerColor = SurfaceWhite,
            disabledContainerColor = AppBackground,
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = BorderLight,
            errorBorderColor = AccentOrange,
            cursorColor = PrimaryBlue,
            focusedLabelColor = PrimaryBlue,
            unfocusedLabelColor = TextSecondary
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        enabled = enabled,
        isError = isError,
        supportingText = if (isError && errorMessage != null) {
            { Text(errorMessage, style = MaterialTheme.typography.bodySmall.copy(color = AccentOrange)) }
        } else null
    )
}

/**
 * Password Text Field
 * Includes toggle visibility icon automatically
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "••••••••",
    label: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }

    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        label = label,
        leadingIcon = Icons.Default.Lock,
        trailingIcon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        keyboardType = KeyboardType.Password,
        isError = isError,
        errorMessage = errorMessage
    )
}

/**
 * Email Text Field
 * Pre-configured for email input with email icon
 */
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "example@driverhub.com",
    label: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        label = label,
        leadingIcon = Icons.Default.Email,
        trailingIcon = if (value.isNotEmpty() && value.contains("@")) Icons.Default.CheckCircle else null,
        keyboardType = KeyboardType.Email,
        isError = isError,
        errorMessage = errorMessage
    )
}

/**
 * Phone Text Field
 * Pre-configured for phone number input with phone icon
 */
@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "+91 12345 67890",
    label: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        label = label,
        leadingIcon = Icons.Default.Phone,
        keyboardType = KeyboardType.Phone,
        isError = isError,
        errorMessage = errorMessage
    )
}

/**
 * Search Text Field
 * Pre-configured for search with search icon
 */
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    onClearClick: (() -> Unit)? = null
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = Icons.Default.Search,
        trailingIcon = if (value.isNotEmpty() && onClearClick != null) Icons.Default.Close else null,
        onTrailingIconClick = onClearClick,
        keyboardType = KeyboardType.Text
    )
}
