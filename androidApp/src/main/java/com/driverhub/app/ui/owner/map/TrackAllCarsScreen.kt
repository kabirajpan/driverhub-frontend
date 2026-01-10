package com.driverhub.app.ui.owner.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
fun TrackAllCarsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Track All Cars",
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "6 cars active",
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
            MapPlaceholder(title = "All Fleet Vehicles")
            
            // Bottom car list
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        AppBackground.copy(alpha = 0.95f),
                        shape = RoundedCornerShape(topStart = AppRadius.ExtraLarge, topEnd = AppRadius.ExtraLarge)
                    )
                    .padding(AppSpacing.Default)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Cars",
                        fontSize = AppFontSize.Subtitle,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
                    ) {
                        FilterChip(
                            selected = true,
                            onClick = { },
                            label = { Text("Active (6)") }
                        )
                        FilterChip(
                            selected = false,
                            onClick = { },
                            label = { Text("Idle (0)") }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.Medium))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium)
                ) {
                    items(mockCarList()) { car ->
                        CarQuickCard(
                            carName = car.name,
                            driverName = car.driver,
                            speed = car.speed,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CarQuickCard(
    carName: String,
    driverName: String,
    speed: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(AppRadius.Medium),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
    ) {
        Column(
            modifier = Modifier.padding(AppSpacing.Medium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DriveEta,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(AppSpacing.Small))
                Text(
                    text = carName,
                    fontSize = AppFontSize.Body,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(AppSpacing.Small))
            
            Text(
                text = driverName,
                fontSize = AppFontSize.ExtraSmall,
                color = TextSecondary
            )
            
            Spacer(modifier = Modifier.height(AppSpacing.Small))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Speed,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = speed,
                    fontSize = AppFontSize.Body,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }
        }
    }
}

private data class MockCar(val name: String, val driver: String, val speed: String)

private fun mockCarList() = listOf(
    MockCar("Toyota Camry", "Rajesh Kumar", "45 km/h"),
    MockCar("Honda City", "Amit Sharma", "60 km/h"),
    MockCar("Maruti Swift", "Priya Patel", "0 km/h"),
    MockCar("Hyundai Creta", "Vikram Singh", "55 km/h"),
    MockCar("Tata Nexon", "Neha Gupta", "40 km/h"),
    MockCar("Mahindra XUV", "Rahul Verma", "0 km/h")
)
