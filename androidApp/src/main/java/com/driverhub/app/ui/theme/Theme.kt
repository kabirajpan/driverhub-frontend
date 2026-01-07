package com.driverhub.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Driver Hub Light Color Scheme
private val DriverHubLightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = TextWhite,
    primaryContainer = BlueTint,
    onPrimaryContainer = PrimaryBlue,
    
    secondary = AccentOrange,
    onSecondary = TextWhite,
    secondaryContainer = OrangeTint,
    onSecondaryContainer = AccentOrange,
    
    tertiary = SuccessGreen,
    onTertiary = TextWhite,
    tertiaryContainer = GreenTint,
    onTertiaryContainer = SuccessGreen,
    
    background = AppBackground,
    onBackground = TextPrimary,
    
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = AppBackground,
    onSurfaceVariant = TextSecondary,
    
    outline = BorderLight,
    outlineVariant = BorderMedium,
    
    error = AccentOrange,
    onError = TextWhite,
    errorContainer = OrangeTint,
    onErrorContainer = AccentOrange
)

/**
 * Driver Hub App Theme
 * 
 * Usage:
 * ```
 * @Composable
 * fun MyScreen() {
 *     DriverHubTheme {
 *         // Your composables here
 *     }
 * }
 * ```
 */
@Composable
fun DriverHubTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DriverHubLightColorScheme,
        typography = DriverHubTypography,
        shapes = DriverHubShapes,
        content = content
    )
}

// Helper extension properties for easy access in composables
object Theme {
    val colors = DriverHubLightColorScheme
    val typography = DriverHubTypography
    val shapes = DriverHubShapes
    val spacing = AppSpacing
    val sizes = AppSizes
    val radius = AppRadius
    val elevation = AppElevation
    val border = AppBorder
    val fontSize = AppFontSize
    val textStyles = AppTextStyles
    val customShapes = AppShapes
}
