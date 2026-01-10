package com.driverhub.app.ui.owner.cars.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.driverhub.app.ui.common.*
import com.driverhub.app.ui.theme.*

@Composable
fun ActiveCarCard(
    carName: String,
    licensePlate: String,
    driverName: String,
    status: String,
    speed: String,
    location: String,
    onTrackClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    StandardCard(
        modifier = modifier.fillMaxWidth(),
        elevation = com.driverhub.app.ui.common.CardElevation.LEVEL_1
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.Default)
        ) {
            // Header: Car name and status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = carName,
                        fontSize = AppFontSize.Title,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = licensePlate,
                        fontSize = AppFontSize.Body,
                        color = TextSecondary
                    )
                }
                
                // Status chip using common component
                CarStatusChip(status = status)
            }
            
            Spacer(modifier = Modifier.height(AppSpacing.Medium))
            
            // Driver info with icon avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DriverAvatar(size = AvatarSize.MEDIUM)
                
                Spacer(modifier = Modifier.width(AppSpacing.Medium))
                
                Column {
                    Text(
                        text = "Driver",
                        fontSize = AppFontSize.ExtraSmall,
                        color = TextSecondary
                    )
                    Text(
                        text = driverName,
                        fontSize = AppFontSize.Body,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(AppSpacing.Medium))
            
            // Speed and Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Speed
                InfoRow(
                    icon = Icons.Default.Speed,
                    text = speed
                )
                
                // Location
                InfoRow(
                    icon = Icons.Default.LocationOn,
                    text = location,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            
            Spacer(modifier = Modifier.height(AppSpacing.Medium))
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Small)
            ) {
                OutlinedButton(
                    onClick = onTrackClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null,
                        modifier = Modifier.size(AppSizes.IconSmall)
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.ExtraSmall))
                    Text("Track")
                }
                
                OutlinedButton(
                    onClick = onCallClick,
                    modifier = Modifier.weight(1f),
                    enabled = driverName != "Unassigned"
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = null,
                        modifier = Modifier.size(AppSizes.IconSmall)
                    )
                    Spacer(modifier = Modifier.width(AppSpacing.ExtraSmall))
                    Text("Call")
                }
            }
        }
    }
}

/**
 * Reusable info row with icon and text
 */
@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TextSecondary,
            modifier = Modifier.size(AppSizes.IconSmall)
        )
        Spacer(modifier = Modifier.width(AppSpacing.Small))
        Text(
            text = text,
            fontSize = AppFontSize.Body,
            color = TextPrimary,
            maxLines = 1
        )
    }
}
