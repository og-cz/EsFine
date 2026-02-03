package com.example.esfine.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.components.MoodScanner
import com.example.esfine.ui.theme.*
import com.example.esfine.ui.viewmodel.MainViewModel

@Composable
fun CoreMixerScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mixerState by viewModel.mixerUiState.collectAsState()

    // Main container
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Main UI Content Layer
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Visualizer Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {
                // Preset Trigger Button
                Button(
                    onClick = { viewModel.showMoodScanner(true) },
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundSecondary)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ScatterPlot, null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (mixerState.activePreset != null) "PROTOCOL: ${mixerState.activePreset}" else "INITIATE_DIAGNOSTIC_SCAN",
                            style = EsFineTypography.labelSmall,
                            color = TextCharcoal
                        )
                    }
                }

                // Entity Visualizer
                Box(
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.Center)
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (mixerState.isPlaying) 360f else 0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(10000, easing = LinearEasing)
                        ),
                        label = "outer_ring"
                    )

                    val middleRotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (mixerState.isPlaying) -180f else 0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(8000, easing = FastOutSlowInEasing)
                        ),
                        label = "middle_shape"
                    )

                    val coreScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = if (mixerState.isPlaying) 0.9f else 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(4000, easing = FastOutSlowInEasing)
                        ),
                        label = "core_scale"
                    )

                    val scale by animateFloatAsState(targetValue = if (mixerState.isPlaying) 1.05f else 1f, label = "scale")
                    val coreRotation by animateFloatAsState(targetValue = if (mixerState.isPlaying) 45f else 0f, label = "core_rotation")

                    Box(modifier = Modifier.fillMaxSize().scale(scale).rotate(rotation).border(1.dp, TextCharcoal.copy(0.2f), CircleShape))
                    Box(modifier = Modifier.size(192.dp).align(Alignment.Center).rotate(middleRotation).border(1.dp, AccentSage.copy(0.6f), RoundedCornerShape(50)))
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.Center)
                            .rotate(coreRotation)
                            .scale(coreScale)
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (mixerState.isPlaying) TextCharcoal else BackgroundSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(if (mixerState.isPlaying) AccentSage else TextMuted))
                    }
                }

                // Status text (Inside the Visualizer Box)
                androidx.compose.animation.AnimatedVisibility(
                    visible = mixerState.isPlaying,
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text("SYSTEM_RESONANCE_ACTIVE", style = EsFineTypography.labelSmall, color = AccentSage)
                }
            }

            // Faders / Controls Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CardWhite)
                    .border(1.dp, BorderMuted, RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                ChannelFader("TEXTURE", mixerState.textureLevel, Icons.Default.WaterDrop, AccentSage)
                Spacer(modifier = Modifier.height(24.dp))
                ChannelFader("MELODY", mixerState.melodyLevel, Icons.Default.VolumeUp, TextCharcoal)

                Spacer(modifier = Modifier.height(32.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = { viewModel.togglePlayback() },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TextCharcoal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(if (mixerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow, null)
                        Spacer(Modifier.width(12.dp))
                        Text(if (mixerState.isPlaying) "PAUSE" else "INITIATE")
                    }

                    IconButton(onClick = { viewModel.resetMixer() }, modifier = Modifier.size(56.dp)) {
                        Icon(Icons.Default.Refresh, "Reset", tint = TextCharcoal)
                    }
                }
            }
        }

        // --- MoodScanner Overlay ---
        // Explicitly using the full package name to avoid Scope ambiguity
        androidx.compose.animation.AnimatedVisibility(
            visible = mixerState.showMoodScanner,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Force a background here so it covers the mixer completely
            Box(modifier = Modifier.fillMaxSize().background(BackgroundOffWhite)) {
                MoodScanner(
                    onClose = { viewModel.showMoodScanner(false) },
                    onSelectPreset = { preset -> viewModel.applyPreset(preset) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ChannelFader(label: String, level: Int, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(18.dp), tint = tint)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(label, style = EsFineTypography.labelSmall, color = TextCharcoal)
                Text("$level%", style = EsFineTypography.labelSmall, color = TextMuted)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(Color(0xFFF0F0F0))) {
                Box(modifier = Modifier.fillMaxWidth(level / 100f).fillMaxSize().background(tint))
            }
        }
    }
}
