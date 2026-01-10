package com.driverhub.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.theme.*

/**
 * Reusable status chip component
 * Shows status with appropriate color coding
 */
@Composable
fun StatusChip(
    text: String,
    status: ChipStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor) = when (status) {
        ChipStatus.SUCCESS -> GreenTint to SuccessGreen
        ChipStatus.WARNING -> OrangeTint to AccentOrange
        ChipStatus.INFO -> BlueTint to PrimaryBlue
        ChipStatus.ERROR -> Color(0xFFFFEBEE) to Color(0xFFD32F2F)
        ChipStatus.NEUTRAL -> Color(0xFFF5F5F5) to TextSecondary
    }
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(AppRadius.Round))
            .background(backgroundColor)
            .padding(horizontal = AppSpacing.Medium, vertical = AppSpacing.Small)
    ) {
        Text(
            text = text,
            fontSize = AppFontSize.ExtraSmall,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

/**
 * Chip status types for different use cases
 */
enum class ChipStatus {
    SUCCESS,  // Green - Active, Completed, Success
    WARNING,  // Orange - Idle, Pending, Warning
    INFO,     // Blue - Information, Default
    ERROR,    // Red - Failed, Error, Critical
    NEUTRAL   // Grey - Disabled, Inactive
}

/**
 * Convenience functions for common car statuses
 */
@Composable
fun CarStatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val chipStatus = when (status.uppercase()) {
        "ACTIVE" -> ChipStatus.SUCCESS
        "IDLE" -> ChipStatus.WARNING
        "MAINTENANCE" -> ChipStatus.ERROR
        "INACTIVE" -> ChipStatus.NEUTRAL
        else -> ChipStatus.INFO
    }
    
    StatusChip(
        text = status,
        status = chipStatus,
        modifier = modifier
    )
}

/**
 * Convenience function for driver statuses
 */
@Composable
fun DriverStatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val chipStatus = when (status.uppercase()) {
        "AVAILABLE" -> ChipStatus.SUCCESS
        "ON_TRIP", "ON TRIP" -> ChipStatus.INFO
        "OFFLINE" -> ChipStatus.NEUTRAL
        "ON_BREAK", "ON BREAK" -> ChipStatus.WARNING
        "SUSPENDED" -> ChipStatus.ERROR
        else -> ChipStatus.INFO
    }
    
    StatusChip(
        text = status.replace("_", " "),
        status = chipStatus,
        modifier = modifier
    )
}
