package com.example.esfine.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.esfine.R
import com.example.esfine.audio.AudioEngine
import com.example.esfine.ui.components.MoodScanner
import com.example.esfine.ui.theme.*
import com.example.esfine.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun CoreMixerScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mixerState by viewModel.mixerUiState.collectAsState()
    val context = LocalContext.current

    // ================= AUDIO =================
    val texturePool = remember {
        intArrayOf(
            R.raw.night_rain_1,
            R.raw.night_rain_2,
            R.raw.vinyl_1,
            R.raw.vinyl_2,
            R.raw.brown_1
        )
    }

    val melodyPool = remember {
        intArrayOf(
            R.raw.theta_1,
            R.raw.theta_2
        )
    }

    val audioEngine = remember { AudioEngine(context.applicationContext) }

    DisposableEffect(Unit) {
        onDispose { audioEngine.release() }
    }

    LaunchedEffect(mixerState.isPlaying) {
        if (mixerState.isPlaying) audioEngine.start(texturePool, melodyPool)
        else audioEngine.stop()
    }

    LaunchedEffect(mixerState.textureLevel) {
        audioEngine.setTextureVolume(mixerState.textureLevel / 100f)
    }

    LaunchedEffect(mixerState.melodyLevel) {
        audioEngine.setMelodyVolume(mixerState.melodyLevel / 100f)
    }

    LaunchedEffect(mixerState.randomizeNonce) {
        if (mixerState.isPlaying) {
            audioEngine.randomize(texturePool, melodyPool)
        }
    }

    // ================= IDLE MOOD PROMPT =================
    var showMoodPrompt by remember { mutableStateOf(false) }
    var interactionNonce by remember { mutableIntStateOf(0) }
    val onUserInteraction = { interactionNonce++ }

    LaunchedEffect(interactionNonce, mixerState.showMoodScanner) {
        if (mixerState.showMoodScanner) {
            showMoodPrompt = false
            return@LaunchedEffect
        }

        showMoodPrompt = false
        delay(Random.nextLong(3 * 60_000L, 5 * 60_000L))
        showMoodPrompt = true
    }

    // ================= UI =================
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            // ===== VISUALIZER =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 16.dp)
            ) {

                Button(
                    onClick = {
                        onUserInteraction()
                        viewModel.showMoodScanner(true)
                    },
                    modifier = Modifier.align(Alignment.TopCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundSecondary)
                ) {
                    Icon(Icons.Default.ScatterPlot, null, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = mixerState.activePreset ?: "INITIATE_DIAGNOSTIC_SCAN",
                        style = EsFineTypography.labelSmall,
                        color = TextCharcoal
                    )
                }

                val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

                val rotation by infiniteTransition.animateFloat(
                    0f, if (mixerState.isPlaying) 360f else 0f,
                    infiniteRepeatable(tween(10000, easing = LinearEasing))
                )

                val middleRotation by infiniteTransition.animateFloat(
                    0f, if (mixerState.isPlaying) -180f else 0f,
                    infiniteRepeatable(tween(8000, easing = FastOutSlowInEasing))
                )

                val coreScale by infiniteTransition.animateFloat(
                    1f, if (mixerState.isPlaying) 0.9f else 1f,
                    infiniteRepeatable(tween(4000, easing = FastOutSlowInEasing))
                )

                Box(
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(rotation)
                            .border(1.dp, TextCharcoal.copy(alpha = 0.2f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(192.dp)
                            .align(Alignment.Center)
                            .rotate(middleRotation)
                            .border(1.dp, AccentSage, RoundedCornerShape(50))
                    )
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.Center)
                            .scale(coreScale)
                            .clip(RoundedCornerShape(24.dp))
                            .background(TextCharcoal)
                    )
                }

                androidx.compose.animation.AnimatedVisibility(

                    visible = mixerState.isPlaying,
                    modifier = Modifier.align(Alignment.BottomCenter),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text("SYSTEM_RESONANCE_ACTIVE", color = AccentSage)
                }
            }

            // ===== CONTROLS =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CardWhite)
                    .border(1.dp, BorderMuted, RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {

                ChannelFaderSlider(
                    "TEXTURE",
                    mixerState.textureLevel,
                    Icons.Default.WaterDrop,
                    AccentSage
                ) {
                    onUserInteraction()
                    viewModel.setTextureLevel(it)
                }

                Spacer(Modifier.height(24.dp))

                ChannelFaderSlider(
                    "MELODY",
                    mixerState.melodyLevel,
                    Icons.AutoMirrored.Filled.VolumeUp,
                    TextCharcoal
                ) {
                    onUserInteraction()
                    viewModel.setMelodyLevel(it)
                }

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        onUserInteraction()
                        viewModel.togglePlayback()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = TextCharcoal)
                ) {
                    Icon(
                        if (mixerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        null
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(if (mixerState.isPlaying) "PAUSE" else "INITIATE")
                }
            }
        }

        // ===== MOOD SCANNER =====
        AnimatedVisibility(
            visible = mixerState.showMoodScanner,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            MoodScanner(
                onClose = {
                    onUserInteraction()
                    viewModel.showMoodScanner(false)
                },
                onSelectPreset = {
                    onUserInteraction()
                    viewModel.applyPreset(it)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // ===== IDLE POPUP =====
        if (showMoodPrompt) {
            AlertDialog(
                onDismissRequest = {
                    showMoodPrompt = false
                    onUserInteraction()
                },
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.border(1.dp, Color.Gray),
                title = { Text("Mood Calibration") },
                text = { Text("You’ve been idle. Select a mood to calibrate the mix.") },
                confirmButton = {
                    TextButton(onClick = {
                        showMoodPrompt = false
                        onUserInteraction()
                        viewModel.showMoodScanner(true)
                    }) { Text("SELECT MOOD") }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showMoodPrompt = false
                        onUserInteraction()
                    }) { Text("LATER") }
                }
            )
        }
    }
}

@Composable
private fun ChannelFaderSlider(
    label: String,
    level: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    onChange: (Int) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = tint)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(label)
                Slider(
                    value = level.toFloat(),
                    onValueChange = { onChange(it.toInt()) },
                    valueRange = 0f..100f
                )
            }
        }
    }
}
