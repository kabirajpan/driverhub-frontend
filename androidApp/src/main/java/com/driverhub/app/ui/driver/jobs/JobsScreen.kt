package com.driverhub.app.ui.driver.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.theme.*

@Composable
fun JobsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Driver Jobs Screen",
            fontSize = AppFontSize.Heading,
            color = TextPrimary
        )
    }
}
