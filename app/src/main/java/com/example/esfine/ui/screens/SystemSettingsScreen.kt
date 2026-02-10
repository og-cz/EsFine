package com.example.esfine.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.state.ThemeMode
import com.example.esfine.ui.viewmodel.MainViewModel

@Composable
fun SystemSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mainUiState by viewModel.mainUiState.collectAsState()
    val isDark = mainUiState.themeMode == ThemeMode.DARK

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp)
        ) {

            SettingRow(
                icon = Icons.Default.Settings,
                title = "Audio setup",
                value = "Standard"
            )

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)

            // ✅ Night mode toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.DarkMode, contentDescription = null)
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Night mode",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isDark,
                    onCheckedChange = { viewModel.toggleTheme() }
                )
            }

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)

            SettingRow(
                icon = Icons.Default.Storage,
                title = "Offline storage",
                value = "Local"
            )

            Divider(color = MaterialTheme.colorScheme.surfaceVariant)

            SettingRow(
                icon = Icons.Default.Shield,
                title = "Privacy",
                value = "Offline only"
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tip: Night mode is better for low light and long sessions.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
