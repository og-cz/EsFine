package com.example.espin.ui.components

import androidx.compose.foundation.border
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.espin.domain.model.Mood
import com.example.espin.domain.model.MoodPreset
import com.example.espin.ui.theme.AccentSage
import com.example.espin.ui.theme.BackgroundOffWhite
import com.example.espin.ui.theme.BackgroundSecondary
import com.example.espin.ui.theme.BorderMuted
import com.example.espin.ui.theme.EspinTypography
import com.example.espin.ui.theme.TextCharcoal
import com.example.espin.ui.theme.TextMuted
import com.example.espin.ui.theme.TextSecondary

/**
 * MoodScanner overlay component matching MoodScanner.tsx
 * Uses BoxWithConstraints to ensure minimum 48dp touch targets across resolutions
 */
@Composable
fun MoodScanner(
    onClose: () -> Unit,
    onSelectPreset: (MoodPreset) -> Unit,
    modifier: Modifier = Modifier
) {
    // Moods array matching MoodScanner.tsx
    val moods = listOf(
        Mood(
            id = Mood.ID_SCATTERED,
            label = Mood.LABEL_SCATTERED,
            icon = Icons.Default.WaterDrop, // Wind icon equivalent
            description = Mood.DESC_SCATTERED,
            preset = MoodPreset.GROUNDING
        ),
        Mood(
            id = Mood.ID_DRAINED,
            label = Mood.LABEL_DRAINED,
            icon = Icons.Default.BatteryChargingFull, // Battery icon
            description = Mood.DESC_DRAINED,
            preset = MoodPreset.RECHARGE
        ),
        Mood(
            id = Mood.ID_FOCUSED,
            label = Mood.LABEL_FOCUSED,
            icon = Icons.Default.Bolt, // Zap icon
            description = Mood.DESC_FOCUSED,
            preset = MoodPreset.DEEP_WORK
        ),
        Mood(
            id = Mood.ID_OVERLOAD,
            label = Mood.LABEL_OVERLOAD,
            icon = Icons.Default.Cloud, // Cloud icon
            description = Mood.DESC_OVERLOAD,
            preset = MoodPreset.RESET
        )
    )
    
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 20 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { 20 }),
        modifier = modifier
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundOffWhite) // bg-[#F5F5F5]
                .padding(24.dp) // p-6
        ) {
            // Calculate button size ensuring minimum 48dp touch target
            // Grid: 2 columns, gap-4 (16dp), padding 24dp
            val availableWidth = maxWidth - (24.dp * 2) // Total padding
            val gap = 16.dp // gap-4
            val buttonWidth = ((availableWidth - gap) / 2).coerceAtLeast(48.dp)
            
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header: flex items-center justify-between mb-8
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DIAGNOSTIC_INPUT",
                        style = EspinTypography.titleMedium, // text-xl
                        color = TextCharcoal,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextCharcoal
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp)) // mb-8 equivalent
                
                // Description text
                Text(
                    text = "SELECT CURRENT COGNITIVE STATE FOR CALIBRATION:",
                    style = EspinTypography.bodySmall, // text-sm
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 24.dp) // mb-6
                )
                
                // Grid: grid-cols-2 gap-4
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(gap),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(gap)
                ) {
                    items(moods) { mood ->
                        MoodButton(
                            label = mood.label,
                            description = mood.description,
                            icon = mood.icon,
                            onClick = { onSelectPreset(mood.preset) },
                            modifier = Modifier.width(buttonWidth)
                        )
                    }
                }
                
                // Footer info box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)) // rounded-xl
                        .background(BackgroundSecondary.copy(alpha = 0.3f)) // bg-[#E0E0E0]/30
                        .border(
                            width = 1.dp,
                            color = BorderMuted,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp) // p-4
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp) // mb-2
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology, // Brain icon
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = AccentSage
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // gap-2
                            Text(
                                text = "AI_CALIBRATION_READY",
                                style = EspinTypography.labelSmall, // text-[10px]
                                color = TextCharcoal
                            )
                        }
                        Text(
                            text = "Selecting a state will automatically configure the audio engine frequencies for optimal neural entrainment.",
                            style = EspinTypography.labelSmall, // text-[10px]
                            color = TextSecondary
                        )
                    }
                }
            }
        }
    }
}
