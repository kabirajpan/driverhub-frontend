package com.driverhub.app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }
    
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(30)
            progress += 0.02f
        }
        delay(300)
        onSplashComplete()
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFE8EDF2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            
            // Blue rounded square with floating shadow effect
            Surface(
                modifier = Modifier.size(120.dp),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFF1565F5),
                shadowElevation = 16.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalTaxi,
                        contentDescription = "Driver Hub",
                        modifier = Modifier.size(64.dp),
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "Driver Hub",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "Professional Drivers. On Demand.",
                fontSize = 16.sp,
                color = Color(0xFF6B7280)
            )
            
            Spacer(modifier = Modifier.weight(1.5f))
            
            // Rounded progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .width(200.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF1565F5),
                trackColor = Color(0xFFD1D5DB),
                strokeCap = StrokeCap.Round
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "v1.0",
                fontSize = 14.sp,
                color = Color(0xFF9CA3AF)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
