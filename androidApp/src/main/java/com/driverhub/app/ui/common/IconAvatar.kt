package com.driverhub.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

/**
 * Reusable circular icon avatar component
 * Used for driver profiles, action icons, etc.
 */
@Composable
fun IconAvatar(
    icon: ImageVector,
    contentDescription: String?,
    backgroundColor: Color,
    iconColor: Color,
    size: Dp,
    iconSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * Predefined avatar sizes
 */
enum class AvatarSize(val containerSize: Dp, val iconSize: Dp) {
    SMALL(32.dp, 18.dp),
    MEDIUM(40.dp, 24.dp),
    LARGE(56.dp, 32.dp),
    EXTRA_LARGE(72.dp, 40.dp)
}

/**
 * Predefined avatar styles with colors
 */
enum class AvatarStyle(val backgroundColor: Color, val iconColor: Color) {
    PRIMARY(BlueTint, PrimaryBlue),
    SECONDARY(OrangeTint, AccentOrange),
    SUCCESS(GreenTint, SuccessGreen),
    NEUTRAL(Color(0xFFF5F5F5), TextSecondary)
}

/**
 * Convenience function with predefined size and style
 */
@Composable
fun IconAvatar(
    icon: ImageVector,
    contentDescription: String? = null,
    size: AvatarSize = AvatarSize.MEDIUM,
    style: AvatarStyle = AvatarStyle.PRIMARY,
    modifier: Modifier = Modifier
) {
    IconAvatar(
        icon = icon,
        contentDescription = contentDescription,
        backgroundColor = style.backgroundColor,
        iconColor = style.iconColor,
        size = size.containerSize,
        iconSize = size.iconSize,
        modifier = modifier
    )
}

/**
 * Driver avatar - preset for driver profiles
 */
@Composable
fun DriverAvatar(
    size: AvatarSize = AvatarSize.MEDIUM,
    modifier: Modifier = Modifier
) {
    IconAvatar(
        icon = Icons.Default.Person,
        contentDescription = "Driver",
        size = size,
        style = AvatarStyle.PRIMARY,
        modifier = modifier
    )
}
