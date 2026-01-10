package com.driverhub.app.ui.owner.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.owner.map.components.MapPlaceholder
import com.driverhub.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackMyCarScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Track My Car",
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Currently driving",
                            fontSize = AppFontSize.ExtraSmall,
                            color = TextSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
            MapPlaceholder(title = "Your Location")
            
            // Top info card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.Default)
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(AppRadius.Medium),
                colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                elevation = CardDefaults.cardElevation(defaultElevation = AppElevation.Level2)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.Default),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Toyota Camry",
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "MP 09 AB 1234",
                            fontSize = AppFontSize.Body,
                            color = TextSecondary
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .background(GreenTint, RoundedCornerShape(AppRadius.Round))
                            .padding(horizontal = AppSpacing.Medium, vertical = AppSpacing.Small)
                    ) {
                        Text(
                            text = "Driving",
                            fontSize = AppFontSize.ExtraSmall,
                            fontWeight = FontWeight.Medium,
                            color = SuccessGreen
                        )
                    }
                }
            }
            
            // Bottom stats panel
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        SurfaceWhite,
                        shape = RoundedCornerShape(topStart = AppRadius.ExtraLarge, topEnd = AppRadius.ExtraLarge)
                    )
                    .padding(AppSpacing.ExtraLarge)
            ) {
                Text(
                    text = "Trip Stats",
                    fontSize = AppFontSize.Subtitle,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Spacer(modifier = Modifier.height(AppSpacing.Default))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StatItem(
                        icon = Icons.Default.Speed,
                        label = "Speed",
                        value = "45 km/h"
                    )
                    
                    StatItem(
                        icon = Icons.Default.AccessTime,
                        label = "Duration",
                        value = "1h 20m"
                    )
                    
                    StatItem(
                        icon = Icons.Default.Route,
                        label = "Distance",
                        value = "32.5 km"
                    )
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentOrange)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.Small))
                        Text("Share Location")
                    }
                    
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.StopCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.Small))
                        Text("End Trip")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(AppSpacing.Small))
        Text(
            text = value,
            fontSize = AppFontSize.Title,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = label,
            fontSize = AppFontSize.ExtraSmall,
            color = TextSecondary
        )
    }
}
