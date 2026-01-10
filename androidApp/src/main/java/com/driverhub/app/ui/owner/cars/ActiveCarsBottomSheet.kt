package com.driverhub.app.ui.owner.cars

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.common.EmptyState
import com.driverhub.app.ui.common.LoadingIndicator
import com.driverhub.app.ui.owner.cars.components.ActiveCarCard
import com.driverhub.app.ui.theme.*
import com.driverhub.shared.domain.model.CarStatus
import com.driverhub.shared.presentation.owner.cars.ActiveCarsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveCarsBottomSheet(
    viewModel: ActiveCarsViewModel,
    onDismiss: () -> Unit,
    onTrackCar: (String) -> Unit = {},
    onCallDriver: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Trigger refresh when sheet opens
    LaunchedEffect(Unit) {
        viewModel.refreshCars()
    }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = AppBackground,
        shape = RoundedCornerShape(topStart = AppRadius.ExtraLarge, topEnd = AppRadius.ExtraLarge),
        dragHandle = null,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.Default),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Active Cars",
                        fontSize = AppFontSize.Heading,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${uiState.activeCount} active • ${uiState.idleCount} idle",
                        fontSize = AppFontSize.Body,
                        color = TextSecondary
                    )
                }
                
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextSecondary
                    )
                }
            }
            
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = AppSpacing.Default),
                color = BorderLight
            )
            
            // Filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppSpacing.Default),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.Small)
            ) {
                FilterChip(
                    selected = uiState.selectedFilter == null,
                    onClick = { viewModel.filterByStatus(null) },
                    label = { Text("All (${uiState.totalCount})") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == CarStatus.ACTIVE,
                    onClick = { viewModel.filterByStatus(CarStatus.ACTIVE) },
                    label = { Text("Active (${uiState.activeCount})") }
                )
                FilterChip(
                    selected = uiState.selectedFilter == CarStatus.IDLE,
                    onClick = { viewModel.filterByStatus(CarStatus.IDLE) },
                    label = { Text("Idle (${uiState.idleCount})") }
                )
            }
            
            // Content area
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when {
                    // Loading state
                    uiState.isLoading -> {
                        LoadingIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    
                    // Error state
                    uiState.error != null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppSpacing.ExtraLarge),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            EmptyState(
                                icon = Icons.Default.Error,
                                title = "Error Loading Cars",
                                subtitle = uiState.error ?: "Unknown error"
                            )
                            Spacer(modifier = Modifier.height(AppSpacing.Default))
                            Button(
                                onClick = { viewModel.loadCars() }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                    
                    // Empty state
                    uiState.showEmptyState -> {
                        EmptyState(
                            icon = Icons.Default.DirectionsCar,
                            title = "No Cars Available",
                            subtitle = "There are no cars in your fleet yet.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    
                    // Success - show list
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = AppSpacing.Default),
                            verticalArrangement = Arrangement.spacedBy(AppSpacing.Medium),
                            contentPadding = PaddingValues(vertical = AppSpacing.Default)
                        ) {
                            items(
                                items = uiState.displayList,
                                key = { car -> car.id }
                            ) { car ->
                                ActiveCarCard(
                                    carName = car.name,
                                    licensePlate = car.licensePlate,
                                    driverName = car.currentDriver?.name ?: "Unassigned",
                                    status = car.status.name,
                                    speed = "${car.currentSpeed.toInt()} km/h",
                                    location = car.location?.address ?: "Unknown",
                                    onTrackClick = { onTrackCar(car.id) },
                                    onCallClick = { 
                                        car.currentDriver?.let { onCallDriver(it.phone) }
                                    }
                                )
                            }
                        }
                    }
                }
                
                // Pull to refresh indicator
                if (uiState.isRefreshing) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
    
    // Cleanup when dismissed
    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearError()
        }
    }
}
