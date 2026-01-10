package com.driverhub.app.ui.driver.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.zIndex
import com.driverhub.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DriverBottomBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    onNavigateToMap: (String) -> Unit = {}
) {
    var isSpeedDialExpanded by remember { mutableStateOf(false) }
    var isZoomAnimating by remember { mutableStateOf(false) }
    
    val zoomScale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        // Bottom Nav Surface
        Surface(
            color = AppBackground,
            shadowElevation = AppElevation.Level2,
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.Default, vertical = AppSpacing.Small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Home
                BottomNavItem(
                    icon = Icons.Default.Home,
                    label = "Home",
                    isSelected = selectedTab == "home",
                    onClick = { onTabSelected("home") },
                    modifier = Modifier.weight(1f)
                )
                
                // Jobs
                BottomNavItem(
                    icon = Icons.Default.Work,
                    label = "Jobs",
                    isSelected = selectedTab == "jobs",
                    onClick = { onTabSelected("jobs") },
                    modifier = Modifier.weight(1f)
                )
                
                // Empty space for FAB
                Spacer(modifier = Modifier.weight(1f))
                
                // Notifications
                BottomNavItem(
                    icon = Icons.Default.Notifications,
                    label = "Notifications",
                    isSelected = selectedTab == "notifications",
                    onClick = { onTabSelected("notifications") },
                    modifier = Modifier.weight(1f)
                )
                
                // Earnings
                BottomNavItem(
                    icon = Icons.Default.AccountBalanceWallet,
                    label = "Earnings",
                    isSelected = selectedTab == "earnings",
                    onClick = { onTabSelected("earnings") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Speed Dial Container
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-13).dp)
                .zIndex(if (isZoomAnimating) 10f else 1f)
        ) {
            // Speed Dial Button 1 - Left Diagonal (Current Location)
            AnimatedSpeedDialButton(
                visible = isSpeedDialExpanded,
                targetOffsetX = (-80).dp,
                targetOffsetY = (-80).dp,
                icon = Icons.Default.MyLocation,
                containerColor = PrimaryBlue,
                onClick = {
                    isSpeedDialExpanded = false
                    onNavigateToMap("current_location")
                }
            )
            
            // Speed Dial Button 2 - Middle Up (Navigate to Job)
            AnimatedSpeedDialButton(
                visible = isSpeedDialExpanded,
                targetOffsetX = 0.dp,
                targetOffsetY = (-100).dp,
                icon = Icons.Default.Navigation,
                containerColor = AccentOrange,
                onClick = {
                    isSpeedDialExpanded = false
                    onNavigateToMap("navigate_job")
                }
            )
            
            // Speed Dial Button 3 - Right Diagonal (Traffic View)
            AnimatedSpeedDialButton(
                visible = isSpeedDialExpanded,
                targetOffsetX = 80.dp,
                targetOffsetY = (-80).dp,
                icon = Icons.Default.Traffic,
                containerColor = SuccessGreen,
                onClick = {
                    isSpeedDialExpanded = false
                    onNavigateToMap("traffic_view")
                }
            )
            
            // Main Map FAB with Press and Hold Effect
            val rotation by animateFloatAsState(
                targetValue = if (isSpeedDialExpanded) 45f else 0f,
                animationSpec = tween(durationMillis = 300),
                label = "rotation"
            )
            
            val haptic = LocalHapticFeedback.current
            val interactionSource = remember { MutableInteractionSource() }
            
            // Detect pressed state for animation
            val isPressed by interactionSource.collectIsPressedAsState()
            
            // Animated scale when pressed (not during zoom animation)
            val scale by animateFloatAsState(
                targetValue = if (isPressed && !isZoomAnimating) 0.88f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "scale"
            )
            
            // Animated elevation when pressed
            val elevation by animateFloatAsState(
                targetValue = if (isPressed) 12f else 6f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "elevation"
            )
            
            // Animated alpha when pressed
            val alpha by animateFloatAsState(
                targetValue = if (isPressed) 0.85f else 1f,
                animationSpec = tween(durationMillis = 100),
                label = "alpha"
            )
            
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        // Use zoom scale when animating, otherwise use press scale
                        val finalScale = if (isZoomAnimating) zoomScale.value else scale
                        scaleX = finalScale
                        scaleY = finalScale
                        this.alpha = alpha
                        shape = CircleShape
                        clip = true
                        shadowElevation = elevation
                        spotShadowColor = Color.Black.copy(alpha = 0.3f)
                        ambientShadowColor = Color.Black.copy(alpha = 0.15f)
                    }
                    .background(Color.Black, CircleShape)
                    .combinedClickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            isSpeedDialExpanded = !isSpeedDialExpanded
                        },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            isSpeedDialExpanded = false
                            isZoomAnimating = true
                            
                            scope.launch {
                                // Start zoom animation
                                zoomScale.animateTo(
                                    targetValue = 50f,
                                    animationSpec = tween(
                                        durationMillis = 400,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                                // Navigate after animation
                                onNavigateToMap("full_map")
                                // Reset after a small delay
                                delay(100)
                                zoomScale.snapTo(1f)
                                isZoomAnimating = false
                            }
                        }
                    )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotation)
                ) {
                    Icon(
                        imageVector = if (isSpeedDialExpanded) Icons.Default.Close else Icons.Default.Map,
                        contentDescription = "Map",
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .graphicsLayer {
                                // Fade out icon during zoom
                                this.alpha = if (isZoomAnimating) 0f else 1f
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedSpeedDialButton(
    visible: Boolean,
    targetOffsetX: Dp,
    targetOffsetY: Dp,
    icon: ImageVector,
    containerColor: Color,
    onClick: () -> Unit
) {
    val offsetX by animateDpAsState(
        targetValue = if (visible) targetOffsetX else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "offsetX"
    )
    
    val offsetY by animateDpAsState(
        targetValue = if (visible) targetOffsetY else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "offsetY"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "alpha"
    )
    
    if (alpha > 0f) {
        Box(
            modifier = Modifier
                .offset(x = offsetX, y = offsetY)
        ) {
            FloatingActionButton(
                onClick = onClick,
                containerColor = containerColor.copy(alpha = alpha),
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .padding(horizontal = AppSpacing.ExtraSmall)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) PrimaryBlue else TextSecondary,
            modifier = Modifier.size(AppSizes.IconDefault)
        )
        
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) PrimaryBlue else TextSecondary,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}
