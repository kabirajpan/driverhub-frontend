package com.driverhub.app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.theme.*

/**
 * Generic Social Button
 * Can be customized for any social provider
 */
@Composable
fun SocialButton(
    text: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = SurfaceWhite,
    outlined: Boolean = true
) {
    if (outlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(AppSizes.ButtonHeightMedium),
            enabled = enabled,
            shape = AppShapes.ButtonMedium,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
                disabledContainerColor = AppBackground
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = AppBorder.Thin,
                brush = androidx.compose.ui.graphics.SolidColor(if (enabled) BorderLight else BorderMedium)
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (enabled) iconTint else TextTertiary,
                modifier = Modifier.size(AppSizes.IconDefault)
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = text,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (enabled) TextPrimary else TextTertiary
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.height(AppSizes.ButtonHeightMedium),
            enabled = enabled,
            shape = AppShapes.ButtonMedium,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                disabledContainerColor = BorderMedium
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (enabled) iconTint else TextTertiary,
                modifier = Modifier.size(AppSizes.IconDefault)
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = text,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = if (enabled) TextWhite else TextTertiary
            )
        }
    }
}

/**
 * Google Sign In Button
 */
@Composable
fun GoogleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Google"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.Email, // Using Email icon as placeholder for Google
        iconTint = GoogleRed,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        outlined = true
    )
}

/**
 * Facebook Sign In Button
 */
@Composable
fun FacebookButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Facebook"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.Facebook,
        iconTint = TextWhite,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        backgroundColor = FacebookBlue,
        outlined = false
    )
}

/**
 * Apple Sign In Button
 */
@Composable
fun AppleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Apple"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.PhoneIphone, // Using Phone icon as placeholder for Apple
        iconTint = AppleBlack,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        outlined = true
    )
}

/**
 * Row of Social Login Buttons
 * Google and Apple/Facebook side by side
 */
@Composable
fun SocialLoginRow(
    onGoogleClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    secondaryButton: @Composable (Modifier, Boolean, () -> Unit) -> Unit = { mod, isEnabled, click ->
        AppleButton(onClick = click, modifier = mod, enabled = isEnabled)
    }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
    ) {
        GoogleButton(
            onClick = onGoogleClick,
            modifier = Modifier.weight(1f),
            enabled = enabled
        )
        
        secondaryButton(Modifier.weight(1f), enabled, onSecondaryClick)
    }
}

/**
 * Divider with "OR CONTINUE WITH" text
 * Commonly used before social login buttons
 */
@Composable
fun SocialLoginDivider(
    modifier: Modifier = Modifier,
    text: String = "OR CONTINUE WITH"
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BorderMedium
        )
        Text(
            text = text,
            style = AppTextStyles.Overline,
            modifier = Modifier.padding(horizontal = AppSpacing.Medium)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = BorderMedium
        )
    }
}
