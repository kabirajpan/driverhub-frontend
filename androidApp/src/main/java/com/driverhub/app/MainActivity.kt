package com.driverhub.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.driverhub.app.ui.auth.*
import com.driverhub.app.ui.common.SplashScreen
import com.driverhub.app.ui.owner.navigation.OwnerMainScreen
import com.driverhub.app.ui.driver.navigation.DriverMainScreen  // ← ADDED
import com.driverhub.app.ui.theme.DriverHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hardcoded role for testing (change to "driver" to switch POV)
        val userRole = "driver"  // ← Change to "driver" to test driver UI
        
        // Set status bar and navigation bar to match app background
        val appBackgroundColor = Color(0xFFE8EDF2)
        window.statusBarColor = appBackgroundColor.toArgb()
        window.navigationBarColor = appBackgroundColor.toArgb()
        
        // Make status bar icons dark (visible on light background)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
        
        setContent {
            DriverHubTheme {
                var showSplash by remember { mutableStateOf(true) }
                var navigationStack by remember { mutableStateOf(listOf("login")) }
                
                val currentScreen = navigationStack.lastOrNull() ?: "login"
                
                // Navigate to a new screen
                val navigateTo: (String) -> Unit = { screen ->
                    navigationStack = navigationStack + screen
                }
                
                // Go back
                val navigateBack: () -> Unit = {
                    if (navigationStack.size > 1) {
                        navigationStack = navigationStack.dropLast(1)
                    } else {
                        // If we're at the root (login), finish the activity
                        finish()
                    }
                }
                
                // Handle system back gesture/button
                BackHandler(enabled = !showSplash && navigationStack.size > 1) {
                    navigateBack()
                }
                
                when {
                    showSplash -> {
                        SplashScreen(onSplashComplete = { 
                            showSplash = false 
                        })
                    }
                    
                    currentScreen == "login" -> {
                        LoginScreen(
                            onLoginClick = { 
                                navigateTo("main")
                            },
                            onSignUpClick = { 
                                navigateTo("register")
                            },
                            onForgotPasswordClick = {
                                navigateTo("forgot_password")
                            }
                        )
                    }
                    
                    currentScreen == "register" -> {
                        RegisterScreen(
                            onBackClick = navigateBack,
                            onLoginClick = { 
                                // Go back to login (pop back to it)
                                navigationStack = listOf("login")
                            },
                            onRegisterComplete = { 
                                // Go back to login after registration
                                navigationStack = listOf("login")
                            }
                        )
                    }
                    
                    currentScreen == "forgot_password" -> {
                        ForgotPasswordScreen(
                            onBackClick = navigateBack,
                            onLoginClick = {
                                // Go back to login
                                navigationStack = listOf("login")
                            },
                            onPasswordResetComplete = {
                                // Go back to login after reset
                                navigationStack = listOf("login")
                            }
                        )
                    }
                    
                    currentScreen == "main" -> {
                        when (userRole) {
                            "owner" -> OwnerMainScreen()
                            "driver" -> DriverMainScreen()  // ← UPDATED: Use DriverMainScreen
                            else -> {
                                Surface(modifier = Modifier.fillMaxSize()) {
                                    androidx.compose.material3.Text("Unknown Role")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
