package com.driverhub.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.driverhub.app.ui.theme.*

@Composable
fun SideDrawer(
    userName: String = "User Name",
    userRole: String = "DRIVER", // "DRIVER" or "CAR_OWNER"
    stat1Label: String = "TODAY'S EARNINGS",
    stat1Value: String = "$142.50",
    stat2Label: String = "JOBS DONE",
    stat2Value: String = "12",
    onDocumentsClick: () -> Unit = {},
    onEarningsClick: () -> Unit = {},
    onJobHistoryClick: () -> Unit = {},
    onSupportClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    ModalDrawerSheet(
        drawerContainerColor = SurfaceWhite,
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = AppSpacing.ExtraLarge)
            ) {
                Spacer(modifier = Modifier.height(AppSpacing.Default))
                
                // Profile Section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Picture with Online Indicator
                    Box {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(OrangeTint),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                tint = AccentOrange,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        
                        // Online indicator
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                                .align(Alignment.BottomEnd)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(AppSpacing.Default))
                    
                    Column {
                        Text(
                            text = userName,
                            fontSize = AppFontSize.Title,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        
                        Text(
                            text = if (userRole == "DRIVER") "Verified Driver" else "Fleet Owner",
                            fontSize = AppFontSize.Body,
                            color = TextSecondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
                
                // Stats Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AppRadius.Medium),
                    color = AppBackground
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppSpacing.Large),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Stat 1
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stat1Label,
                                fontSize = AppFontSize.ExtraSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
                            Text(
                                text = stat1Value,
                                fontSize = AppFontSize.HeadingLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        
                        // Divider
                        VerticalDivider(
                            modifier = Modifier
                                .height(50.dp)
                                .padding(horizontal = AppSpacing.Default),
                            color = BorderLight
                        )
                        
                        // Stat 2
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stat2Label,
                                fontSize = AppFontSize.ExtraSmall,
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(AppSpacing.ExtraSmall))
                            Text(
                                text = stat2Value,
                                fontSize = AppFontSize.HeadingLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
                
                // ACTIVITIES Section
                Text(
                    text = if (userRole == "DRIVER") "ACTIVITIES" else "MANAGEMENT",
                    fontSize = AppFontSize.ExtraSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextTertiary,
                    modifier = Modifier.padding(bottom = AppSpacing.Small)
                )
                
                // Menu Items
                if (userRole == "DRIVER") {
                    DrawerMenuItem(
                        icon = Icons.Default.Badge,
                        iconColor = PrimaryBlue,
                        iconBackground = BlueTint,
                        title = "My Documents & License",
                        onClick = onDocumentsClick
                    )
                    
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    DrawerMenuItem(
                        icon = Icons.Default.AccountBalance,
                        iconColor = SuccessGreen,
                        iconBackground = GreenTint,
                        title = "Earnings History",
                        onClick = onEarningsClick
                    )
                    
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    DrawerMenuItem(
                        icon = Icons.Default.History,
                        iconColor = AccentOrange,
                        iconBackground = OrangeTint,
                        title = "Job History",
                        onClick = onJobHistoryClick
                    )
                } else {
                    // Owner menu items
                    DrawerMenuItem(
                        icon = Icons.Default.DirectionsCar,
                        iconColor = PrimaryBlue,
                        iconBackground = BlueTint,
                        title = "My Vehicles",
                        onClick = onDocumentsClick
                    )
                    
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    DrawerMenuItem(
                        icon = Icons.Default.AccountBalance,
                        iconColor = SuccessGreen,
                        iconBackground = GreenTint,
                        title = "Wallet & Earnings",
                        onClick = onEarningsClick
                    )
                    
                    Spacer(modifier = Modifier.height(AppSpacing.Small))
                    
                    DrawerMenuItem(
                        icon = Icons.Default.VerifiedUser,
                        iconColor = AccentOrange,
                        iconBackground = OrangeTint,
                        title = "Documents & Verification",
                        onClick = onJobHistoryClick
                    )
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
                
                // SUPPORT & SETTINGS Section
                Text(
                    text = "SUPPORT & SETTINGS",
                    fontSize = AppFontSize.ExtraSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextTertiary,
                    modifier = Modifier.padding(bottom = AppSpacing.Small)
                )
                
                DrawerMenuItem(
                    icon = Icons.Default.Help,
                    iconColor = PrimaryBlue,
                    iconBackground = BlueTint,
                    title = "Support & FAQ",
                    onClick = onSupportClick
                )
                
                Spacer(modifier = Modifier.height(AppSpacing.Small))
                
                DrawerMenuItem(
                    icon = Icons.Default.Settings,
                    iconColor = PrimaryBlue,
                    iconBackground = BlueTint,
                    title = "App Settings",
                    onClick = onSettingsClick
                )
                
                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
            }
            
            // Fixed bottom section (Logout + Version)
            Column(
                modifier = Modifier.padding(AppSpacing.ExtraLarge)
            ) {
                // Logout Button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onLogoutClick),
                    shape = RoundedCornerShape(AppRadius.Medium),
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(AppSpacing.Medium),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(AppSizes.IconDefault)
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.Small))
                        Text(
                            text = "Logout",
                            fontSize = AppFontSize.BodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(AppSpacing.Medium))
                
                // App version
                Text(
                    text = "Driver Hub v4.2.0 • Build 882",
                    fontSize = AppFontSize.ExtraSmall,
                    color = TextTertiary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    iconColor: Color,
    iconBackground: Color,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(AppRadius.Medium),
        color = AppBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(AppRadius.Small))
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(AppSizes.IconDefault)
                )
            }
            
            Spacer(modifier = Modifier.width(AppSpacing.Medium))
            
            Text(
                text = title,
                fontSize = AppFontSize.BodyLarge,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = TextTertiary,
                modifier = Modifier.size(AppSizes.IconMedium)
            )
        }
    }
}
