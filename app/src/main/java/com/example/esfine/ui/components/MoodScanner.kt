package com.example.esfine.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.esfine.domain.model.Mood
import com.example.esfine.domain.model.MoodPreset

@Composable
fun MoodScanner(
    onClose: () -> Unit,
    onSelectPreset: (MoodPreset) -> Unit,
    modifier: Modifier = Modifier
) {
    val moods = listOf(
        Mood(Mood.ID_SCATTERED, Mood.LABEL_SCATTERED, Icons.Default.WaterDrop, "Quiet and steady", MoodPreset.GROUNDING),
        Mood(Mood.ID_DRAINED, Mood.LABEL_DRAINED, Icons.Default.BatteryChargingFull, "Gentle boost", MoodPreset.RECHARGE),
        Mood(Mood.ID_FOCUSED, Mood.LABEL_FOCUSED, Icons.Default.Bolt, "Stay on track", MoodPreset.DEEP_WORK),
        Mood(Mood.ID_OVERLOAD, Mood.LABEL_OVERLOAD, Icons.Default.Cloud, "Slow down and reset", MoodPreset.RESET)
    )

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 20 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { 20 }),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mood check-in",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "How are you feeling right now?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(moods) { mood ->
                    MoodButton(
                        label = mood.label,
                        description = mood.description,
                        icon = mood.icon,
                        onClick = { onSelectPreset(mood.preset) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Tip: Pick a mood and we’ll adjust the mix for you.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
