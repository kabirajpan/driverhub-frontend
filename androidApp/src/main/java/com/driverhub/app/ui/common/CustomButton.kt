package com.driverhub.app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.driverhub.app.ui.theme.*

/**
 * Primary Button - Blue background with white text
 * Used for main actions like "Log In", "Continue", "Create Account"
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    height: Dp = AppSizes.ButtonHeightLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        enabled = enabled && !isLoading,
        shape = AppShapes.ButtonMedium,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = TextWhite,
            disabledContainerColor = BorderMedium,
            disabledContentColor = TextTertiary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppElevation.Level1,
            pressedElevation = AppElevation.Level2
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(AppSizes.IconMedium),
                color = TextWhite,
                strokeWidth = AppBorder.Medium
            )
        } else {
            Text(
                text = text,
                style = AppTextStyles.ButtonText
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(AppSpacing.Small))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(AppSizes.IconMedium)
                )
            }
        }
    }
}

/**
 * Secondary Button - Orange background with white text
 * Used for secondary actions like "Register", "Create Account"
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    height: Dp = AppSizes.ButtonHeightLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        enabled = enabled && !isLoading,
        shape = AppShapes.ButtonMedium,
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentOrange,
            contentColor = TextWhite,
            disabledContainerColor = BorderMedium,
            disabledContentColor = TextTertiary
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = AppElevation.Level1,
            pressedElevation = AppElevation.Level2
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(AppSizes.IconMedium),
                color = TextWhite,
                strokeWidth = AppBorder.Medium
            )
        } else {
            Text(
                text = text,
                style = AppTextStyles.ButtonText
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(AppSpacing.Small))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(AppSizes.IconMedium)
                )
            }
        }
    }
}

/**
 * Outline Button - White background with border
 * Used for alternative actions or social login buttons
 */
@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    iconTint: Color = TextPrimary,
    height: Dp = AppSizes.ButtonHeightMedium
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(height),
        enabled = enabled && !isLoading,
        shape = AppShapes.ButtonMedium,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = SurfaceWhite,
            contentColor = TextPrimary,
            disabledContainerColor = AppBackground,
            disabledContentColor = TextTertiary
        ),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = AppBorder.Thin,
            brush = androidx.compose.ui.graphics.SolidColor(BorderLight)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(AppSizes.IconDefault),
                color = PrimaryBlue,
                strokeWidth = AppBorder.Medium
            )
        } else {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(AppSizes.IconDefault)
                )
                Spacer(modifier = Modifier.width(AppSpacing.Small))
            }
            Text(
                text = text,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }
    }
}

/**
 * Text Button - Transparent background with colored text
 * Used for "Forgot Password", "Skip", etc.
 */
@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = PrimaryBlue
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = AppTextStyles.Link.copy(color = color)
        )
    }
}
