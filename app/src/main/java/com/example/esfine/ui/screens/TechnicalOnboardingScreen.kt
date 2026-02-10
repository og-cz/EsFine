package com.example.esfine.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.EsFineTypography
import com.example.esfine.ui.viewmodel.MainViewModel

data class OnboardingStep(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val description: String,
    val code: String
)

@Composable
fun TechnicalOnboardingScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val onboardingState by viewModel.onboardingUiState.collectAsState()

    val steps = listOf(
        OnboardingStep(
            id = 0,
            title = "Auditory Immersion",
            icon = Icons.Default.Headphones,
            description = "Start calming ambient layers to reduce mental load.",
            code = "INIT_AUDIO_PROTOCOL"
        ),
        OnboardingStep(
            id = 1,
            title = "Behavior Sync",
            icon = Icons.Default.Radio,
            description = "The app adapts the mix based on your selected mood.",
            code = "SYNC_BEHAVIOR_CORE"
        ),
        OnboardingStep(
            id = 2,
            title = "Cognitive Grounding",
            icon = Icons.Default.Psychology,
            description = "Quick grounding prompts help you reset when needed.",
            code = "EXEC_GROUNDING_VOICE"
        )
    )

    val scheme = MaterialTheme.colorScheme
    val bg = scheme.background
    val surface = scheme.surface
    val surfaceVariant = scheme.surfaceVariant
    val onSurface = scheme.onSurface
    val outline = scheme.outline
    val secondaryText = scheme.onSurfaceVariant

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 32.dp, vertical = 80.dp)
    ) {
        // subtle background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            onSurface.copy(alpha = 0.04f)
                        )
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {

            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp)
                    .border(
                        width = 1.dp,
                        color = outline.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "System Initialization",
                    style = EsFineTypography.titleMedium,
                    color = onSurface
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    val pulseAlpha by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = tween(1000),
                        label = "pulse"
                    )

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(AccentSage.copy(alpha = pulseAlpha))
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Step ${onboardingState.currentStep + 1}/${steps.size}",
                        style = EsFineTypography.bodySmall,
                        color = secondaryText
                    )
                }
            }

            // Content Slider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AnimatedContent(
                    targetState = onboardingState.currentStep,
                    transitionSpec = {
                        (fadeIn() + slideInHorizontally { 50 }).togetherWith(
                            fadeOut() + slideOutHorizontally { -50 }
                        )
                    },
                    label = "step_content"
                ) { stepIndex ->
                    val currentStep = steps[stepIndex]

                    Column(modifier = Modifier.fillMaxSize()) {

                        // Icon container
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, outline.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                                .background(surface)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = currentStep.icon,
                                contentDescription = currentStep.title,
                                modifier = Modifier.size(32.dp),
                                tint = AccentSage
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = currentStep.title,
                            style = EsFineTypography.titleLarge,
                            color = onSurface,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = currentStep.description,
                            style = EsFineTypography.bodyMedium,
                            color = secondaryText,
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // Code box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(surfaceVariant)
                                .border(
                                    width = 1.dp,
                                    color = outline.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp)
                                .padding(bottom = 32.dp)
                        ) {
                            Text(
                                text = "> ${currentStep.code}...\n> Status: Standby",
                                style = EsFineTypography.labelSmall,
                                color = onSurface.copy(alpha = 0.75f)
                            )
                        }
                    }
                }
            }

            // Footer Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Step indicators
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    steps.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index == onboardingState.currentStep) onSurface
                                    else outline.copy(alpha = 0.45f)
                                )
                        )
                    }
                }

                // Next / Initialize button
                TextButton(
                    onClick = {
                        if (onboardingState.currentStep < steps.size - 1) {
                            viewModel.onboardingNext()
                        } else {
                            viewModel.onboardingComplete()
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(onSurface)
                        .padding(horizontal = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = if (onboardingState.currentStep == steps.size - 1) "Initialize" else "Next",
                            style = EsFineTypography.bodySmall,
                            color = bg
                        )

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(bg),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (onboardingState.currentStep == steps.size - 1)
                                    Icons.Default.Check else Icons.Default.ChevronRight,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
