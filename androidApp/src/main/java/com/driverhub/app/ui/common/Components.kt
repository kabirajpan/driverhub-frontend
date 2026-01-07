package com.driverhub.app.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.theme.*

/**
 * Role Selection Card
 * Used in registration for selecting Driver or Car Owner role
 */
@Composable
fun RoleCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(AppSizes.CardHeightMedium)
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = AppBorder.Medium,
                        color = PrimaryBlue,
                        shape = AppShapes.CardMedium
                    )
                } else Modifier
            ),
        shape = AppShapes.CardMedium,
        color = SurfaceWhite,
        shadowElevation = if (isSelected) AppElevation.Level2 else AppElevation.Level1
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
                shape = AppShapes.IconContainer,
                color = iconColor.copy(alpha = 0.1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(AppSizes.IconLarge)
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
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
                Text(
                    text = subtitle,
                    fontSize = AppFontSize.Small,
                    color = TextSecondary,
                    lineHeight = AppFontSize.Title
                )
            }

            // Radio Button
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryBlue,
                    unselectedColor = BorderMedium
                )
            )
        }
    }
}

/**
 * Icon Container Surface
 * Used for displaying icons with colored background (like app icon)
 */
@Composable
fun IconContainer(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color = PrimaryBlue,
    iconTint: Color = TextWhite,
    iconSize: androidx.compose.ui.unit.Dp = AppSizes.IconExtraLarge,
    containerSize: androidx.compose.ui.unit.Dp = AppSizes.AppIconMedium,
    shape: androidx.compose.ui.graphics.Shape = AppShapes.IconContainer,
    elevation: androidx.compose.ui.unit.Dp = AppElevation.Level3
) {
    Surface(
        modifier = modifier.size(containerSize),
        shape = shape,
        color = containerColor,
        shadowElevation = elevation
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

/**
 * Loading Indicator
 * Centered circular progress indicator
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    text: String? = null
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = PrimaryBlue,
            strokeWidth = AppBorder.Medium
        )
        if (text != null) {
            Spacer(modifier = Modifier.height(AppSpacing.Default))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        }
    }
}

/**
 * Empty State
 * Shows when there's no data to display
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    actionButton: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(AppSpacing.ExtraLarge2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier.size(AppSizes.IconMassive)
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(color = TextPrimary)
        )
        
        Spacer(modifier = Modifier.height(AppSpacing.Small))
        
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
        )
        
        if (actionButton != null) {
            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
            actionButton()
        }
    }
}

/**
 * Progress Indicator (like the one in SplashScreen)
 */
@Composable
fun AppProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .width(AppSizes.ProgressBarWidth)
            .height(AppSizes.ProgressBarHeight)
            .clip(AppShapes.ProgressBar),
        color = PrimaryBlue,
        trackColor = BorderMedium,
        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
    )
}
