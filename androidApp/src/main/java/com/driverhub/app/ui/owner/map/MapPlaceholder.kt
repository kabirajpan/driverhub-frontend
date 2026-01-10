package com.driverhub.app.ui.owner.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

@Composable
fun MapPlaceholder(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE5E5E5)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(AppSpacing.Default))
            Text(
                text = title,
                fontSize = AppFontSize.Title,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(AppSpacing.Small))
            Text(
                text = "Google Maps integration here",
                fontSize = AppFontSize.Body,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun CarMarker(
    carName: String,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(if (isActive) SuccessGreen else AccentOrange),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.DriveEta,
            contentDescription = carName,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun OwnerMarker(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(PrimaryBlue),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "You",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}
