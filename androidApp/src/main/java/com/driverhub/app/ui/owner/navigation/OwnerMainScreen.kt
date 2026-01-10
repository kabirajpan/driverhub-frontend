package com.driverhub.app.ui.owner.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.owner.drivers.DriversScreen
import com.driverhub.app.ui.owner.home.HomeScreen
import com.driverhub.app.ui.owner.jobs.JobsScreen
import com.driverhub.app.ui.owner.map.TrackAllCarsScreen
import com.driverhub.app.ui.owner.map.TrackMyCarScreen
import com.driverhub.app.ui.owner.map.FullMapScreen
import com.driverhub.app.ui.owner.notifications.NotificationScreen
import com.driverhub.shared.data.repository.CarRepositoryImpl
import com.driverhub.shared.domain.usecase.owner.cars.GetActiveCarsUseCase
import com.driverhub.shared.domain.usecase.owner.cars.GetAllCarsUseCase
import com.driverhub.shared.presentation.owner.cars.ActiveCarsViewModel

@Composable
fun OwnerMainScreen() {
    var selectedTab by remember { mutableStateOf("home") }
    var currentMapScreen by remember { mutableStateOf<String?>(null) }
    
    // Create ViewModel instance
    // TODO: Replace with proper DI (Koin) later
    val activeCarsViewModel = remember {
        val repository = CarRepositoryImpl()
        val getActiveCarsUseCase = GetActiveCarsUseCase(repository)
        val getAllCarsUseCase = GetAllCarsUseCase(repository)
        ActiveCarsViewModel(getActiveCarsUseCase, getAllCarsUseCase)
    }
    
    // Cleanup ViewModel when screen is disposed
    DisposableEffect(Unit) {
        onDispose {
            activeCarsViewModel.onCleared()
        }
    }
    
    // Handle system back gesture/button
    BackHandler(enabled = currentMapScreen != null) {
        currentMapScreen = null
    }
    
    // Show map screens if navigated
    when (currentMapScreen) {
        "track_all" -> {
            TrackAllCarsScreen(
                onBackClick = { currentMapScreen = null }
            )
            return
        }
        "track_mine" -> {
            TrackMyCarScreen(
                onBackClick = { currentMapScreen = null }
            )
            return
        }
        "full_map" -> {
            FullMapScreen(
                onBackClick = { currentMapScreen = null }
            )
            return
        }
    }
    
    Scaffold(
        topBar = {
            OwnerTopBar()  // ← ADDED: This was missing!
        },
        bottomBar = {
            OwnerBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onNavigateToMap = { screen -> currentMapScreen = screen },
                activeCarsViewModel = activeCarsViewModel
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "home" -> HomeScreen()
                "drivers" -> DriversScreen()
                "jobs" -> JobsScreen()
                "notification" -> NotificationScreen()
            }
        }
    }
}
