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
import com.driverhub.app.ui.settings.SettingsScreen
import com.driverhub.app.ui.common.SideDrawer
import kotlinx.coroutines.launch

@Composable
fun DriverMainScreen(
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf("home") }
    var currentMapScreen by remember { mutableStateOf<String?>(null) }
    var showSettings by remember { mutableStateOf(false) }

    // Drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Handle system back gesture/button for map screens, settings, and drawer
    BackHandler(enabled = currentMapScreen != null || showSettings || drawerState.isOpen) {
        when {
            drawerState.isOpen -> scope.launch { drawerState.close() }
            showSettings -> showSettings = false
            currentMapScreen != null -> currentMapScreen = null
        }
    }

    // Show settings screen if navigated
    if (showSettings) {
        SettingsScreen(
            onBackClick = { showSettings = false },
            onLogout = onLogout
        )
        return
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

    // Drawer with content
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SideDrawer(
                userName = "Johnathan Doe",
                userRole = "DRIVER",
                stat1Label = "TODAY'S EARNINGS",
                stat1Value = "$142.50",
                stat2Label = "JOBS DONE",
                stat2Value = "12",
                onDocumentsClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to documents screen
                },
                onEarningsClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to earnings history screen
                },
                onJobHistoryClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to job history screen
                },
                onSupportClick = {
                    scope.launch { drawerState.close() }
                    // TODO: Navigate to support screen
                },
                onSettingsClick = {
                    scope.launch { 
                        drawerState.close() 
                        showSettings = true
                    }
                },
                onLogoutClick = {
                    scope.launch { drawerState.close() }
                    onLogout()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                DriverTopBar(
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
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
}
