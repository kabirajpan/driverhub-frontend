package com.driverhub.app.ui.owner.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.owner.home.HomeScreen
import com.driverhub.app.ui.owner.drivers.DriversScreen
import com.driverhub.app.ui.owner.jobs.JobsScreen
import com.driverhub.app.ui.owner.notifications.NotificationScreen
import com.driverhub.app.ui.owner.map.MapScreen
import com.driverhub.app.ui.theme.*

@Composable
fun OwnerMainScreen() {
    var selectedTab by remember { mutableStateOf("home") }
    
    Scaffold(
        topBar = { OwnerTopBar() },
        bottomBar = { 
            OwnerBottomBar(
                selectedTab = selectedTab, 
                onTabSelected = { selectedTab = it }
            ) 
        },
        containerColor = AppBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                "home" -> HomeScreen()
                "drivers" -> DriversScreen()
                "map" -> MapScreen()
                "jobs" -> JobsScreen()
                "notification" -> NotificationScreen()
            }
        }
    }
}
