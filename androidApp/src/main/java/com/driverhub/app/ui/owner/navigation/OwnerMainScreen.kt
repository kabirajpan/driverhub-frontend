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
import com.driverhub.app.ui.common.SideDrawer
import com.driverhub.shared.data.repository.CarRepositoryImpl
import com.driverhub.shared.domain.usecase.owner.cars.GetActiveCarsUseCase
import com.driverhub.shared.domain.usecase.owner.cars.GetAllCarsUseCase
import com.driverhub.shared.presentation.owner.cars.ActiveCarsViewModel
import kotlinx.coroutines.launch

@Composable
fun OwnerMainScreen(
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("home") }
    var currentMapScreen by remember { mutableStateOf<String?>(null) }
    
    // Drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    // Create ViewModel instance ONLY ONCE and dispose properly
    // LaunchedEffect with Unit key ensures this runs only on first composition
    val activeCarsViewModel = remember {
        val repository = CarRepositoryImpl()
        val getActiveCarsUseCase = GetActiveCarsUseCase(repository)
        val getAllCarsUseCase = GetAllCarsUseCase(repository)
        ActiveCarsViewModel(getActiveCarsUseCase, getAllCarsUseCase)
    }
    
    // Cleanup ViewModel when screen is disposed
    DisposableEffect(Unit) {
        onDispose {
            // This ensures ViewModel is cleaned up when OwnerMainScreen is removed
            activeCarsViewModel.onCleared()
        }
    }
    
    // Handle system back gesture/button for map screens and drawer
    BackHandler(enabled = currentMapScreen != null || drawerState.isOpen) {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            currentMapScreen != null -> currentMapScreen = null
        }
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
    
    // Drawer with content
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideDrawer(
                userName = "John Doe",
                userRole = "CAR_OWNER",
                stat1Label = "ACTIVE VEHICLES",
                stat1Value = "8",
                stat2Label = "TOTAL DRIVERS",
                stat2Value = "15",
                onDocumentsClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to vehicles screen
                },
                onEarningsClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to wallet screen
                },
                onJobHistoryClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to documents screen
                },
                onSupportClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to support screen
                },
                onSettingsClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to settings screen
                },
                onLogoutClick = {
                    scope.launch { 
                        drawerState.close()
                        // Small delay to ensure drawer closes before logout
                        kotlinx.coroutines.delay(200)
                        onLogout()
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                OwnerTopBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
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
}
