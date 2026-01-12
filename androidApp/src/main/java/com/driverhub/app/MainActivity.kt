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
import com.driverhub.app.ui.driver.navigation.DriverMainScreen
import com.driverhub.app.ui.theme.DriverHubTheme
import com.driverhub.app.ui.theme.AppBackground
import com.driverhub.shared.domain.repository.TokenStorage
import com.driverhub.shared.domain.usecase.auth.SessionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val tokenStorage: TokenStorage by inject()
    private val sessionUseCase: SessionUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                var userRole by remember { mutableStateOf<String?>(null) }
                var isCheckingSession by remember { mutableStateOf(true) }
                var isLoggingOut by remember { mutableStateOf(false) }
                var sessionCheckTrigger by remember { mutableStateOf(0) }

                // Check for existing session on startup AND after logout
                LaunchedEffect(sessionCheckTrigger) {
                    val accessToken = tokenStorage.getAccessToken()
                    val savedRole = tokenStorage.getUserRole()

                    if (!accessToken.isNullOrBlank() && !savedRole.isNullOrBlank()) {
                        userRole = savedRole
                        currentScreen = "main"
                    } else {
                        userRole = null
                        currentScreen = "login"
                    }
                    isCheckingSession = false
                }

                // Navigate to a new screen
                val navigateTo: (String) -> Unit = { screen ->
                    currentScreen = screen
                }

                // Go back
                val navigateBack: () -> Unit = {
                    when (currentScreen) {
                        "register", "forgot_password" -> currentScreen = "login"
                        "login" -> finish()
                        else -> {
                            currentScreen = "login"
                        }
                    }
                }

                // Handle logout
                val handleLogout: () -> Unit = {
                    if (!isLoggingOut) {
                        isLoggingOut = true
                        
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                tokenStorage.clearTokens()
                                sessionUseCase.logout()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                userRole = null
                                currentScreen = "login"
                                isLoggingOut = false
                                sessionCheckTrigger++
                            }
                        }
                    }
                }

                // Handle system back gesture/button
                BackHandler(enabled = !showSplash && currentScreen != "main" && !isLoggingOut) {
                    navigateBack()
                }

                when {
                    showSplash -> {
                        SplashScreen(onSplashComplete = {
                            showSplash = false
                        })
                    }

                    isCheckingSession -> {
                        Surface(modifier = Modifier.fillMaxSize(), color = AppBackground) {
                            // Empty screen while checking session
                        }
                    }

                    isLoggingOut -> {
                        // Show login screen immediately during logout
                        LoginScreen(
                            onLoginSuccess = { role ->
                                userRole = role
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

                    currentScreen == "login" -> {
                        LoginScreen(
                            onLoginSuccess = { role ->
                                userRole = role
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
                                currentScreen = "login"
                            },
                            onRegisterComplete = {
                                currentScreen = "login"
                            }
                        )
                    }

                    currentScreen == "forgot_password" -> {
                        ForgotPasswordScreen(
                            onBackClick = navigateBack,
                            onLoginClick = {
                                currentScreen = "login"
                            }
                        )
                    }

                    currentScreen == "main" && userRole != null -> {
                        key(userRole) {
                            when (userRole) {
                                "CAR_OWNER" -> OwnerMainScreen(onLogout = handleLogout)
                                "DRIVER" -> DriverMainScreen(onLogout = handleLogout)
                                else -> {
                                    Surface(modifier = Modifier.fillMaxSize()) {
                                        androidx.compose.material3.Text("Unknown Role: $userRole")
                                    }
                                }
                            }
                        }
                    }

                    // Fallback - if we're in an invalid state, show login
                    else -> {
                        LoginScreen(
                            onLoginSuccess = { role ->
                                userRole = role
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
                }
            }
        }
    }
}
