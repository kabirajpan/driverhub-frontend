package com.driverhub.app

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.driverhub.app.ui.theme.DriverHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hardcoded role for testing (change to "driver" to switch POV)
        val userRole = "owner"
        
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
                var currentScreen by remember { mutableStateOf("login") }
                
                when {
                    showSplash -> {
                        SplashScreen(onSplashComplete = { 
                            showSplash = false 
                        })
                    }
                    
                    currentScreen == "login" -> {
                        LoginScreen(
                            onLoginClick = { 
                                currentScreen = "main"
                            },
                            onSignUpClick = { 
                                currentScreen = "register" 
                            },
                            onForgotPasswordClick = {
                                currentScreen = "forgot_password"
                            }
                        )
                    }
                    
                    currentScreen == "register" -> {
                        RegisterScreen(
                            onBackClick = { 
                                currentScreen = "login" 
                            },
                            onLoginClick = { 
                                currentScreen = "login" 
                            },
                            onRegisterComplete = { 
                                currentScreen = "login"
                            }
                        )
                    }
                    
                    currentScreen == "forgot_password" -> {
                        ForgotPasswordScreen(
                            onBackClick = {
                                currentScreen = "login"
                            },
                            onLoginClick = {
                                currentScreen = "login"
                            },
                            onPasswordResetComplete = {
                                currentScreen = "login"
                            }
                        )
                    }
                    
                    currentScreen == "main" -> {
                        when (userRole) {
                            "owner" -> OwnerMainScreen()
                            "driver" -> {
                                Surface(modifier = Modifier.fillMaxSize()) {
                                    androidx.compose.material3.Text("Driver Main Screen")
                                }
                            }
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
