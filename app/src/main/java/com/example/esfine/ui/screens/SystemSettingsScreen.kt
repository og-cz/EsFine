package com.example.esfine.ui.screens

import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.BackgroundSecondary
import com.example.esfine.ui.theme.BorderMuted
import com.example.esfine.ui.theme.CardWhite
import com.example.esfine.ui.theme.EsFineTypography
import com.example.esfine.ui.theme.TextCharcoal
import com.example.esfine.ui.theme.TextMuted
import com.example.esfine.ui.theme.TextSecondary

data class SettingRowData(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val isLast: Boolean = false
)

/**
 * SystemSettings screen matching SystemSettings.tsx
 */
@Composable
fun SystemSettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp) // px-6 pt-8
    ) {
        // Header
        Text(
            text = "SYSTEM\nCONFIGURATION",
            style = EsFineTypography.titleLarge, // text-2xl
            color = TextCharcoal,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 32.dp) // mb-8
        )
        
        // Settings List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)) // rounded-2xl
                .background(CardWhite) // bg-white
                .border(1.dp, BorderMuted, RoundedCornerShape(16.dp))
        ) {
            val settings = listOf(
                SettingRowData(
                    icon = Icons.Default.Settings,
                    label = "Audio Calibration",
                    value = "Standard"
                ),
                SettingRowData(
                    icon = Icons.Default.DarkMode, // Moon
                    label = "Interface Mode",
                    value = "Day"
                ),
                SettingRowData(
                    icon = Icons.Default.Storage, // Database
                    label = "Offline Storage",
                    value = "120MB"
                ),
                SettingRowData(
                    icon = Icons.Default.Shield,
                    label = "Privacy Protocol",
                    value = "Local",
                    isLast = true
                )
            )
            
            settings.forEach { setting ->
                SettingRow(setting = setting)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp)) // mt-8
        
        // System Info Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // rounded-xl
                .background(BackgroundSecondary.copy(alpha = 0.5f)) // bg-[#E0E0E0]/50
                .border(
                    width = 1.dp,
                    color = BorderMuted,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp) // p-4
        ) {
            Text(
                text = "SYS_ID: ESFINE-CORE-882\nBUILD: 2024.10.02_ALPHA\nSTATUS: CONNECTED",
                style = EsFineTypography.labelSmall, // text-[10px]
                color = TextSecondary
            )
        }
    }
}

@Composable
private fun SettingRow(setting: SettingRowData) {
    // 1. Create the interaction source
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                // 2. Use the new ripple here
                indication = rememberRipple(),
                interactionSource = interactionSource,
                onClick = {}
            )
            .padding(16.dp)
            .then(
                if (!setting.isLast) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color(0xFFF0F0F0),
                        shape = RoundedCornerShape(0.dp)
                    )
                } else {
                    Modifier
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = setting.icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = AccentSage
        )
        Spacer(modifier = Modifier.width(16.dp)) // ml-4
        Text(
            text = setting.label,
            style = EsFineTypography.bodyMedium, // text-sm
            color = TextCharcoal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = setting.value,
            style = EsFineTypography.bodySmall, // text-xs
            color = TextMuted
        )
    }
}
