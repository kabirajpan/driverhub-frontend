package com.driverhub.app.ui.driver.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverTopBar(
    onMenuClick: () -> Unit = {}
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = TextPrimary,
                    modifier = Modifier.size(AppSizes.IconLarge)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Message click */ }) {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Messages",
                    tint = TextPrimary,
                    modifier = Modifier.size(AppSizes.IconDefault)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppBackground
        )
    )
}
