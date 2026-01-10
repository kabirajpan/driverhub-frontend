package com.driverhub.app.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

/**
 * Standard card wrapper for consistent styling
 * Use this instead of raw Card composable
 */
@Composable
fun StandardCard(
    modifier: Modifier = Modifier,
    elevation: CardElevation = CardElevation.LEVEL_1,
    backgroundColor: Color = SurfaceWhite,
    cornerRadius: Dp = AppRadius.Medium,
    paddingValues: Dp = AppSpacing.Default,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val elevationDp = when (elevation) {
        CardElevation.LEVEL_0 -> 0.dp
        CardElevation.LEVEL_1 -> AppElevation.Level1
        CardElevation.LEVEL_2 -> AppElevation.Level2
        CardElevation.LEVEL_3 -> AppElevation.Level3
    }
    
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = elevationDp)
        ) {
            content()
        }
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(cornerRadius),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = elevationDp)
        ) {
            content()
        }
    }
}

/**
 * Card elevation levels
 */
enum class CardElevation {
    LEVEL_0,  // No shadow
    LEVEL_1,  // 2dp - Default
    LEVEL_2,  // 4dp - Elevated
    LEVEL_3   // 8dp - Highly elevated
}

/**
 * Preset card variants for common use cases
 */

/**
 * Clickable card with hover effect
 */
@Composable
fun ClickableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    StandardCard(
        modifier = modifier,
        onClick = onClick,
        elevation = CardElevation.LEVEL_1,
        content = content
    )
}

/**
 * Info card with elevated appearance
 */
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    StandardCard(
        modifier = modifier,
        elevation = CardElevation.LEVEL_2,
        backgroundColor = SurfaceWhite,
        content = content
    )
}

/**
 * Compact card with less padding
 */
@Composable
fun CompactCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    StandardCard(
        modifier = modifier,
        onClick = onClick,
        paddingValues = AppSpacing.Small,
        cornerRadius = AppRadius.Small,
        content = content
    )
}
