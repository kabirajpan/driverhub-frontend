package com.driverhub.app.ui.owner.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.theme.*

@Composable
fun MapScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Map Screen",
            fontSize = AppFontSize.Heading,
            color = TextPrimary
        )
    }
}
