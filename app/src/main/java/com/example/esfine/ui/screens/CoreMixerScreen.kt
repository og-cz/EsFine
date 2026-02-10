package com.example.esfine.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.esfine.R
import com.example.esfine.audio.AudioEngine
import com.example.esfine.ui.components.MoodScanner
import com.example.esfine.ui.theme.EsFineTypography
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

    // ==========================
    // AUDIO POOLS (res/raw)
    // ==========================
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

    // Single AudioEngine instance
    val audioEngine = remember { AudioEngine(context.applicationContext) }

    // Release when leaving screen
    DisposableEffect(Unit) {
        onDispose { audioEngine.release() }
    }

    // Start/Stop audio when play state changes
    LaunchedEffect(mixerState.isPlaying) {
        if (mixerState.isPlaying) audioEngine.start(texturePool, melodyPool)
        else audioEngine.stop()
    }

    // Apply volumes
    LaunchedEffect(mixerState.textureLevel) {
        audioEngine.setTextureVolume(mixerState.textureLevel / 100f)
    }
    LaunchedEffect(mixerState.melodyLevel) {
        audioEngine.setMelodyVolume(mixerState.melodyLevel / 100f)
    }

    // Randomize tracks
    LaunchedEffect(mixerState.randomizeNonce) {
        if (mixerState.isPlaying) audioEngine.randomize(texturePool, melodyPool)
    }

    // ==========================
    // IDLE -> MOOD PROMPT (3–5 mins)
    // ✅ Only show when: idle AND NOT playing
    // ==========================
    var showMoodPrompt by remember { mutableStateOf(false) }

    // Increment to reset timer
    var interactionNonce by remember { mutableIntStateOf(0) }
    val onUserInteraction = { interactionNonce++ }

    LaunchedEffect(interactionNonce, mixerState.isPlaying, mixerState.showMoodScanner) {
        // If playing or scanner open, never show prompt
        if (mixerState.isPlaying || mixerState.showMoodScanner) {
            showMoodPrompt = false
            return@LaunchedEffect
        }

        showMoodPrompt = false

        val waitMs = Random.nextLong(3 * 60_000L, 5 * 60_000L + 1)
        delay(waitMs)

        // Still idle + still not playing + scanner not open
        if (!mixerState.isPlaying && !mixerState.showMoodScanner) {
            showMoodPrompt = true
        }
    }

    val cs = MaterialTheme.colorScheme

    // ==========================
    // UI
    // ==========================
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(cs.background)
            // Resets idle timer on any tap
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onUserInteraction()
                        tryAwaitRelease()
                    }
                )
            }
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ===== Visualizer Area =====
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
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cs.surfaceVariant,
                        contentColor = cs.onSurface
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ScatterPlot, null, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (mixerState.activePreset != null)
                                "Preset: ${mixerState.activePreset}"
                            else
                                "Pick a mood",
                            style = EsFineTypography.labelSmall
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(256.dp)
                        .align(Alignment.Center)
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (mixerState.isPlaying) 360f else 0f,
                        animationSpec = infiniteRepeatable(animation = tween(10000, easing = LinearEasing)),
                        label = "outer_ring"
                    )

                    val middleRotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (mixerState.isPlaying) -180f else 0f,
                        animationSpec = infiniteRepeatable(animation = tween(8000, easing = FastOutSlowInEasing)),
                        label = "middle_shape"
                    )

                    val coreScale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = if (mixerState.isPlaying) 0.92f else 1f,
                        animationSpec = infiniteRepeatable(animation = tween(3500, easing = FastOutSlowInEasing)),
                        label = "core_scale"
                    )

                    val scale by animateFloatAsState(
                        targetValue = if (mixerState.isPlaying) 1.04f else 1f,
                        label = "scale"
                    )

                    val coreRotation by animateFloatAsState(
                        targetValue = if (mixerState.isPlaying) 45f else 0f,
                        label = "core_rotation"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(scale)
                            .rotate(rotation)
                            .border(1.dp, cs.onSurface.copy(alpha = 0.18f), CircleShape)
                    )

                    Box(
                        modifier = Modifier
                            .size(192.dp)
                            .align(Alignment.Center)
                            .rotate(middleRotation)
                            .border(1.dp, cs.primary.copy(alpha = 0.55f), RoundedCornerShape(50))
                    )

                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.Center)
                            .rotate(coreRotation)
                            .scale(coreScale)
                            .clip(RoundedCornerShape(24.dp))
                            .background(if (mixerState.isPlaying) cs.onSurface else cs.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (mixerState.isPlaying) cs.primary else cs.onSurfaceVariant)
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = mixerState.isPlaying,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        "Playing",
                        style = EsFineTypography.labelSmall,
                        color = cs.primary
                    )
                }
            }

            // ===== Controls Card =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(cs.surface)
                    .border(1.dp, cs.outline, RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                ChannelFaderSlider(
                    label = "Texture",
                    level = mixerState.textureLevel,
                    icon = Icons.Default.WaterDrop,
                    tint = cs.primary,
                    onChange = {
                        onUserInteraction()
                        viewModel.setTextureLevel(it)
                    }
                )

                Spacer(modifier = Modifier.height(22.dp))

                ChannelFaderSlider(
                    label = "Melody",
                    level = mixerState.melodyLevel,
                    icon = Icons.AutoMirrored.Filled.VolumeUp,
                    tint = cs.onSurface,
                    onChange = {
                        onUserInteraction()
                        viewModel.setMelodyLevel(it)
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onUserInteraction()
                            viewModel.togglePlayback()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cs.surfaceVariant,
                            contentColor = cs.onSurface
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            if (mixerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            null
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(if (mixerState.isPlaying) "Pause" else "Play")
                    }

                    IconButton(
                        onClick = {
                            onUserInteraction()
                            viewModel.randomizeTracks()
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(cs.surfaceVariant)
                            .border(1.dp, cs.outline, RoundedCornerShape(14.dp))
                    ) {
                        Icon(Icons.Default.Refresh, "Refresh", tint = cs.onSurface)
                    }
                }
            }
        }

        // ===== MoodScanner Overlay =====
        androidx.compose.animation.AnimatedVisibility(
            visible = mixerState.showMoodScanner,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize().background(cs.background)) {
                MoodScanner(
                    onClose = {
                        onUserInteraction()
                        viewModel.showMoodScanner(false)
                    },
                    onSelectPreset = { preset ->
                        onUserInteraction()
                        viewModel.applyPreset(preset)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // ===== Idle Mood Prompt (SQUARED + GRAY OUTLINE) =====
        if (showMoodPrompt && !mixerState.showMoodScanner) {
            Dialog(
                onDismissRequest = {
                    showMoodPrompt = false
                    onUserInteraction()
                }
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp), // squared look
                    color = cs.surface,
                    border = BorderStroke(1.dp, cs.outline) // ✅ fixed
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(min = 280.dp, max = 340.dp)
                            .padding(18.dp)
                    ) {
                        Text(
                            text = "Mood check",
                            style = EsFineTypography.bodyMedium,
                            color = cs.onSurface
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "You’ve been idle. Select a mood so the mix can adjust for you.",
                            style = EsFineTypography.bodySmall,
                            color = cs.onSurfaceVariant
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            TextButton(
                                onClick = {
                                    showMoodPrompt = false
                                    onUserInteraction()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .border(1.dp, cs.outline, RoundedCornerShape(6.dp))
                            ) {
                                Text("Later", color = cs.onSurface)
                            }

                            Button(
                                onClick = {
                                    showMoodPrompt = false
                                    onUserInteraction()
                                    viewModel.showMoodScanner(true)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = cs.primary,
                                    contentColor = cs.onPrimary
                                ),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text("Select mood")
                            }
                        }
                    }
                }
            }
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
    val cs = MaterialTheme.colorScheme

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(18.dp), tint = tint)
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(label, style = EsFineTypography.labelSmall, color = cs.onSurface)
                Text("$level%", style = EsFineTypography.labelSmall, color = cs.onSurfaceVariant)
            }

            Slider(
                value = level.toFloat(),
                onValueChange = { onChange(it.toInt()) },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    thumbColor = cs.primary,
                    activeTrackColor = cs.primary,
                    inactiveTrackColor = cs.onSurface.copy(alpha = 0.25f)
                )
            )
        }
    }
}
