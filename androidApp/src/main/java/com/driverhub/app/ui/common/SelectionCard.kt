package com.driverhub.app.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.theme.*

/**
 * Selection Card Component
 * Used for Email/SMS verification method selection
 */
@Composable
fun SelectionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconBackgroundColor: Color = PrimaryBlue
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(AppSizes.AppIconMedium)
            .clickable(enabled = enabled) { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = AppBorder.Medium,
                        color = if (enabled) PrimaryBlue else BorderMedium,
                        shape = AppShapes.CardLarge
                    )
                } else {
                    Modifier.border(
                        width = AppBorder.Thin,
                        color = BorderLight,
                        shape = AppShapes.CardLarge
                    )
                }
            ),
        shape = AppShapes.CardLarge,
        color = if (enabled) SurfaceWhite else AppBackground,
        shadowElevation = if (isSelected && enabled) AppElevation.Level1 else AppElevation.None
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.Default),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Container
            Surface(
                modifier = Modifier.size(AppSizes.IconHuge),
                shape = androidx.compose.foundation.shape.CircleShape,
                color = if (enabled) iconBackgroundColor else BorderMedium
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (enabled) TextWhite else TextTertiary,
                        modifier = Modifier.size(AppSizes.IconDefault)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(AppSpacing.Default))
            
            // Text Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = AppFontSize.Subtitle,
                    fontWeight = FontWeight.SemiBold,
                    color = if (enabled) TextPrimary else TextTertiary
                )
                Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
                Text(
                    text = subtitle,
                    fontSize = AppFontSize.Body,
                    color = if (enabled) TextSecondary else TextTertiary
                )
            }
            
            // Radio Button
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                enabled = enabled,
                colors = RadioButtonDefaults.colors(
                    selectedColor = AccentOrange,
                    unselectedColor = BorderMedium,
                    disabledSelectedColor = BorderMedium,
                    disabledUnselectedColor = BorderMedium
                )
            )
        }
    }
}

/**
 * Role Card Component - Variant for role selection
 * Used in registration flow
 */
@Composable
fun RoleCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    SelectionCard(
        icon = icon,
        title = title,
        subtitle = subtitle,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        iconBackgroundColor = if (enabled) iconColor else BorderMedium
    )
}
