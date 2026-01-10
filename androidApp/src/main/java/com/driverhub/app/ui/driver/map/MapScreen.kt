package com.driverhub.app.ui.driver.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapType: String = "default",
    onBackClick: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar with back button
        TopAppBar(
            title = {
                Text(
                    text = when (mapType) {
                        "current_location" -> "Current Location"
                        "navigate_job" -> "Navigate to Job"
                        "traffic_view" -> "Traffic View"
                        "full_map" -> "Full Map View"
                        else -> "Map"
                    },
                    color = TextPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppBackground
            )
        )
        
        // Map Placeholder
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F4F8)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(AppSpacing.ExtraLarge)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = PrimaryBlue,
                    modifier = Modifier.size(64.dp)
                )
                
                Spacer(modifier = Modifier.height(AppSpacing.Default))
                
                Text(
                    text = when (mapType) {
                        "current_location" -> "Current Location Map"
                        "navigate_job" -> "Navigation to Job"
                        "traffic_view" -> "Live Traffic View"
                        "full_map" -> "Full Map Screen"
                        else -> "Map Screen"
                    },
                    fontSize = AppFontSize.Heading,
                    color = TextPrimary
                )
                
                Spacer(modifier = Modifier.height(AppSpacing.Small))
                
                Text(
                    text = "Map integration coming soon",
                    fontSize = AppFontSize.Body,
                    color = TextSecondary
                )
            }
        }
    }
}
