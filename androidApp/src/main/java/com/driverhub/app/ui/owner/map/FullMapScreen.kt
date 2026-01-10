package com.driverhub.app.ui.owner.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.owner.map.components.MapPlaceholder
import com.driverhub.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullMapScreen(
    onBackClick: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Fleet Overview",
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "6 active • 0 idle",
                            fontSize = AppFontSize.ExtraSmall,
                            color = TextSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Center"
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Layers,
                            contentDescription = "Layers"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppBackground
                )
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Map Placeholder
            MapPlaceholder(title = "All Locations")
            
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.Default)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Small)
            ) {
                FilterChip(
                    selected = selectedFilter == "All",
                    onClick = { selectedFilter = "All" },
                    label = { Text("All (6)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.SelectAll,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
                FilterChip(
                    selected = selectedFilter == "Active",
                    onClick = { selectedFilter = "Active" },
                    label = { Text("Active (6)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DriveEta,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
                FilterChip(
                    selected = selectedFilter == "You",
                    onClick = { selectedFilter = "You" },
                    label = { Text("You") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
            
            // Legend
            Card(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(AppSpacing.Default),
                shape = RoundedCornerShape(AppRadius.Medium),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = AppElevation.Level2)
            ) {
                Column(
                    modifier = Modifier.padding(AppSpacing.Medium)
                ) {
                    Text(
                        text = "Legend",
                        fontSize = AppFontSize.Body,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    LegendItem(
                        color = SuccessGreen,
                        label = "Active Cars",
                        icon = Icons.Default.DriveEta
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    LegendItem(
                        color = AccentOrange,
                        label = "Idle Cars",
                        icon = Icons.Default.DriveEta
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    LegendItem(
                        color = PrimaryBlue,
                        label = "Your Location",
                        icon = Icons.Default.Person
                    )
                }
            }
            
            // Quick actions
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(AppSpacing.Default),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
            ) {
                FloatingActionButton(
                    onClick = { },
                    containerColor = SurfaceWhite,
                    contentColor = TextPrimary,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomIn,
                        contentDescription = "Zoom In"
                    )
                }
                
                FloatingActionButton(
                    onClick = { },
                    containerColor = SurfaceWhite,
                    contentColor = TextPrimary,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ZoomOut,
                        contentDescription = "Zoom Out"
                    )
                }
                
                FloatingActionButton(
                    onClick = { },
                    containerColor = PrimaryBlue,
                    contentColor = SurfaceWhite,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }
    }
}

@Composable
private fun LegendItem(
    color: androidx.compose.ui.graphics.Color,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.Small)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SurfaceWhite,
                modifier = Modifier.size(18.dp)
            )
        }
        
        Text(
            text = label,
            fontSize = AppFontSize.Body,
            color = TextPrimary
        )
    }
}
