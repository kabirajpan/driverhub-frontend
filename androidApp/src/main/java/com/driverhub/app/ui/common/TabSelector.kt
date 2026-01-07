package com.driverhub.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.theme.*

/**
 * Tab Selector Component
 * Used for toggling between Email and Phone Number options
 */
@Composable
fun TabSelector(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    tab1: String = "Email",
    tab2: String = "Phone Number"
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(AppSizes.ButtonHeightMedium),
        shape = AppShapes.ButtonLarge,
        color = SurfaceWhite,
        shadowElevation = AppElevation.Level1
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.ExtraSmall),
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.ExtraSmall)
        ) {
            // Tab 1
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(AppShapes.ButtonMedium)
                    .background(
                        if (selectedTab == tab1) PrimaryBlue else androidx.compose.ui.graphics.Color.Transparent
                    )
                    .clickable { onTabSelected(tab1) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab1,
                    fontSize = AppFontSize.BodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedTab == tab1) TextWhite else TextSecondary
                )
            }

            // Tab 2
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(AppShapes.ButtonMedium)
                    .background(
                        if (selectedTab == tab2) PrimaryBlue else androidx.compose.ui.graphics.Color.Transparent
                    )
                    .clickable { onTabSelected(tab2) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab2,
                    fontSize = AppFontSize.BodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedTab == tab2) TextWhite else TextSecondary
                )
            }
        }
    }
}
