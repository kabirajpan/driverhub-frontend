package com.driverhub.app.ui.owner.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

@Composable
fun OwnerBottomBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        color = SurfaceWhite,
        shadowElevation = AppElevation.Level2
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = AppSpacing.Default),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Home
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedTab == "home",
                onClick = { onTabSelected("home") }
            )
            
            // Drivers
            BottomNavItem(
                icon = Icons.Default.People,
                label = "Drivers",
                isSelected = selectedTab == "drivers",
                onClick = { onTabSelected("drivers") }
            )
            
            // Floating Map Button
            FloatingActionButton(
                onClick = { onTabSelected("map") },
                containerColor = PrimaryBlue,
                contentColor = TextWhite,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Map",
                    modifier = Modifier.size(AppSizes.IconDefault)
                )
            }
            
            // Jobs
            BottomNavItem(
                icon = Icons.Default.Work,
                label = "Jobs",
                isSelected = selectedTab == "jobs",
                onClick = { onTabSelected("jobs") }
            )
            
            // Notification
            BottomNavItem(
                icon = Icons.Default.Notifications,
                label = "Notification",
                isSelected = selectedTab == "notification",
                onClick = { onTabSelected("notification") }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = AppSpacing.Small)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) PrimaryBlue else TextSecondary,
            modifier = Modifier.size(AppSizes.IconDefault)
        )
        Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
        Text(
            text = label,
            fontSize = AppFontSize.ExtraSmall,
            color = if (isSelected) PrimaryBlue else TextSecondary
        )
    }
}
