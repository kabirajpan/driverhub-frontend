package com.driverhub.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Custom Typography for Driver Hub
val DriverHubTypography = Typography(
    // Display styles - Largest text
    displayLarge = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 44.sp,
        color = TextPrimary
    ),
    displayMedium = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp,
        color = TextPrimary
    ),
    displaySmall = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 36.sp,
        color = TextPrimary
    ),
    
    // Headline styles
    headlineLarge = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 32.sp,
        color = TextPrimary
    ),
    headlineMedium = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
        color = TextPrimary
    ),
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        color = TextPrimary
    ),
    
    // Title styles
    titleLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        color = TextPrimary
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
        color = TextPrimary
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        color = TextPrimary
    ),
    
    // Body styles - Regular text
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        color = TextPrimary
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        color = TextPrimary
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 16.sp,
        color = TextSecondary
    ),
    
    // Label styles - For buttons, badges
    labelLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
        color = TextPrimary
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        color = TextPrimary
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        color = TextSecondary
    )
)

// Additional custom text styles for specific use cases
object AppTextStyles {
    // For input field placeholders
    val Placeholder = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        color = TextTertiary
    )
    
    // For input field labels
    val InputLabel = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = TextPrimary
    )
    
    // For button text
    val ButtonText = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextWhite
    )
    
    // For links
    val Link = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = PrimaryBlue
    )
    
    // For captions
    val Caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = TextSecondary
    )
    
    // For overline text (like "OR CONTINUE WITH")
    val Overline = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = TextTertiary,
        letterSpacing = 0.5.sp
    )
}
