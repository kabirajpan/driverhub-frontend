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
    backgroundColor: Color = SurfaceWhite,
    outlined: Boolean = true
) {
    if (outlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.height(AppSizes.ButtonHeightMedium),
            shape = AppShapes.ButtonMedium,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = AppBorder.Thin,
                brush = androidx.compose.ui.graphics.SolidColor(BorderLight)
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = iconTint,
                modifier = Modifier.size(AppSizes.IconDefault)
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = text,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.height(AppSizes.ButtonHeightMedium),
            shape = AppShapes.ButtonMedium,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = iconTint,
                modifier = Modifier.size(AppSizes.IconDefault)
            )
            Spacer(modifier = Modifier.width(AppSpacing.Small))
            Text(
                text = text,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextWhite
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
    text: String = "Google"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.Email, // Using Email icon as placeholder for Google
        iconTint = GoogleRed,
        onClick = onClick,
        modifier = modifier,
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
    text: String = "Facebook"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.Facebook,
        iconTint = TextWhite,
        onClick = onClick,
        modifier = modifier,
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
    text: String = "Apple"
) {
    SocialButton(
        text = text,
        icon = Icons.Default.PhoneIphone, // Using Phone icon as placeholder for Apple
        iconTint = AppleBlack,
        onClick = onClick,
        modifier = modifier,
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
    secondaryButton: @Composable (Modifier, () -> Unit) -> Unit = { mod, click ->
        AppleButton(onClick = click, modifier = mod)
    }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
    ) {
        GoogleButton(
            onClick = onGoogleClick,
            modifier = Modifier.weight(1f)
        )
        
        secondaryButton(Modifier.weight(1f), onSecondaryClick)
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
