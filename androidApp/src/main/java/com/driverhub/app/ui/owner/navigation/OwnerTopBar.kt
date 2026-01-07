package com.driverhub.app.ui.owner.navigation
import androidx.compose.foundation.layout.size          // ← ADD THIS LINE
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.driverhub.app.ui.theme.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerTopBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { /* Profile click */ }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
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
            containerColor = SurfaceWhite
        )
    )
}
