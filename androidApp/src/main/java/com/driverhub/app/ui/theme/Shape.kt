package com.driverhub.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

// Custom Shapes for Driver Hub
val DriverHubShapes = Shapes(
    extraSmall = RoundedCornerShape(AppRadius.ExtraSmall),  // 4dp
    small = RoundedCornerShape(AppRadius.Small),             // 8dp
    medium = RoundedCornerShape(AppRadius.Medium),           // 12dp
    large = RoundedCornerShape(AppRadius.Large),             // 16dp
    extraLarge = RoundedCornerShape(AppRadius.ExtraLarge)    // 20dp
)

// Additional custom shapes for specific components
object AppShapes {
    // For buttons
    val ButtonSmall = RoundedCornerShape(AppRadius.Small)
    val ButtonMedium = RoundedCornerShape(AppRadius.Medium)
    val ButtonLarge = RoundedCornerShape(AppRadius.Large)
    
    // For text fields
    val TextField = RoundedCornerShape(AppRadius.Medium)
    
    // For cards
    val CardSmall = RoundedCornerShape(AppRadius.Medium)
    val CardMedium = RoundedCornerShape(AppRadius.Large)
    val CardLarge = RoundedCornerShape(AppRadius.ExtraLarge)
    
    // For app icon / logo containers
    val IconContainer = RoundedCornerShape(AppRadius.ExtraLarge)
    val IconContainerLarge = RoundedCornerShape(AppRadius.ExtraLarge2)
    
    // For chips, badges
    val Chip = RoundedCornerShape(AppRadius.Round)
    
    // For progress indicators
    val ProgressBar = RoundedCornerShape(AppRadius.ExtraSmall)
    
    // For dialogs, bottom sheets
    val Dialog = RoundedCornerShape(
        topStart = AppRadius.ExtraLarge,
        topEnd = AppRadius.ExtraLarge,
        bottomStart = AppRadius.ExtraLarge,
        bottomEnd = AppRadius.ExtraLarge
    )
    
    val BottomSheet = RoundedCornerShape(
        topStart = AppRadius.ExtraLarge,
        topEnd = AppRadius.ExtraLarge,
        bottomStart = AppRadius.None,
        bottomEnd = AppRadius.None
    )
}
