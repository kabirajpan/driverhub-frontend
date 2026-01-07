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
    iconBackgroundColor: Color = PrimaryBlue
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(AppSizes.AppIconMedium)
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = AppBorder.Medium,
                        color = PrimaryBlue,
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
        color = SurfaceWhite,
        shadowElevation = if (isSelected) AppElevation.Level1 else AppElevation.None
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
                color = iconBackgroundColor
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = TextWhite,
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
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
                Text(
                    text = subtitle,
                    fontSize = AppFontSize.Body,
                    color = TextSecondary
                )
            }

            // Radio Button
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = AccentOrange,
                    unselectedColor = BorderMedium
                )
            )
        }
    }
}
