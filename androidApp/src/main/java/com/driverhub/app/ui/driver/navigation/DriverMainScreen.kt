package com.driverhub.app.ui.driver.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.driver.home.HomeScreen
import com.driverhub.app.ui.driver.jobs.JobsScreen
import com.driverhub.app.ui.driver.notifications.NotificationScreen
import com.driverhub.app.ui.driver.earnings.EarningsScreen
import com.driverhub.app.ui.driver.map.MapScreen

@Composable
fun DriverMainScreen() {
    var selectedTab by remember { mutableStateOf("home") }
    var currentMapScreen by remember { mutableStateOf<String?>(null) }
    
    // Handle system back gesture/button for map screens
    BackHandler(enabled = currentMapScreen != null) {
        currentMapScreen = null
    }
    
    // Show map screens if navigated
    when (currentMapScreen) {
        "current_location" -> {
            MapScreen(
                mapType = "current_location",
                onBackClick = { currentMapScreen = null }
            )
            return
        }
        "navigate_job" -> {
            MapScreen(
                mapType = "navigate_job",
                onBackClick = { currentMapScreen = null }
            )
            return
        }
        "traffic_view" -> {
            MapScreen(
                mapType = "traffic_view",
                onBackClick = { currentMapScreen = null }
            )
            return
        }
        "full_map" -> {
            MapScreen(
                mapType = "full_map",
                onBackClick = { currentMapScreen = null }
            )
            return
        }
    }
    
    Scaffold(
        topBar = {
            DriverTopBar()
        },
        bottomBar = {
            DriverBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onNavigateToMap = { screen -> currentMapScreen = screen }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                "home" -> HomeScreen()
                "jobs" -> JobsScreen()
                "notifications" -> NotificationScreen()
                "earnings" -> EarningsScreen()
            }
        }
    }
}
