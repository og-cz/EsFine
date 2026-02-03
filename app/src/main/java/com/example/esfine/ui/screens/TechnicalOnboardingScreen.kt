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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.BackgroundOffWhite
import com.example.esfine.ui.theme.BackgroundSecondary
import com.example.esfine.ui.theme.BorderMuted
import com.example.esfine.ui.theme.CardWhite
import com.example.esfine.ui.theme.EsFineTypography
import com.example.esfine.ui.theme.TextCharcoal
import com.example.esfine.ui.theme.TextSecondary
import com.example.esfine.ui.viewmodel.MainViewModel

data class OnboardingStep(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val description: String,
    val code: String
)

/**
 * TechnicalOnboarding screen matching TechnicalOnboarding.tsx
 */
@Composable
fun TechnicalOnboardingScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val onboardingState by viewModel.onboardingUiState.collectAsState()
    
    val steps = listOf(
        OnboardingStep(
            id = 0,
            title = "AUDITORY IMMERSION",
            icon = Icons.Default.Headphones,
            description = "System initiates ambient frequencies to lower cognitive load.",
            code = "INIT_AUDIO_PROTOCOL"
        ),
        OnboardingStep(
            id = 1,
            title = "BEHAVIORAL SYNC",
            icon = Icons.Default.Radio,
            description = "The Companion entity mirrors user stability metrics.",
            code = "SYNC_BEHAVIOR_CORE"
        ),
        OnboardingStep(
            id = 2,
            title = "COGNITIVE GROUNDING",
            icon = Icons.Default.Psychology,
            description = "Voice-guided protocols for immediate state correction.",
            code = "EXEC_GROUNDING_VOICE"
        )
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundOffWhite) // bg-[#F5F5F5]
            .padding(horizontal = 32.dp, vertical = 80.dp) // pt-20 px-8 pb-8
    ) {
        // Background Grid (simplified - can be enhanced with Canvas)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            TextCharcoal.copy(alpha = 0.03f)
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 48.dp) // mb-12
                    .border(
                        width = 1.dp,
                        color = TextCharcoal.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(0.dp)
                    )
                    .padding(bottom = 16.dp) // pb-4
            ) {
                Text(
                    text = "SYSTEM INITIALIZATION",
                    style = EsFineTypography.titleMedium, // text-xl
                    color = TextCharcoal
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp) // mt-2
                ) {
                    val pulseAlpha by animateFloatAsState(
                        targetValue = if (true) 1f else 0.3f,
                        animationSpec = tween(1000),
                        label = "pulse"
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp) // w-2 h-2
                            .clip(CircleShape)
                            .background(AccentSage.copy(alpha = pulseAlpha))
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // gap-2
                    Text(
                        text = "SEQUENCE ${onboardingState.currentStep + 1}/${steps.size}",
                        style = EsFineTypography.bodySmall, // text-xs
                        color = TextSecondary
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
                ) { step ->
                    val currentStep = steps[step]
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Icon container: w-20 h-20 -> 80dp
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(16.dp)) // rounded-2xl
                                .border(1.dp, TextCharcoal, RoundedCornerShape(16.dp))
                                .background(BackgroundOffWhite)
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
                        
                        Spacer(modifier = Modifier.height(32.dp)) // mb-8
                        
                        Text(
                            text = currentStep.title,
                            style = EsFineTypography.titleLarge, // text-2xl
                            color = TextCharcoal,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp) // mb-4
                        )
                        
                        Text(
                            text = currentStep.description,
                            style = EsFineTypography.bodyMedium,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 24.dp) // mb-6
                        )
                        
                        Spacer(modifier = Modifier.weight(1f)) // mt-auto
                        
                        // Code box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)) // rounded-lg
                                .background(BackgroundSecondary.copy(alpha = 0.5f)) // bg-[#E0E0E0]/50
                                .border(
                                    width = 2.dp,
                                    color = AccentSage,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp) // p-4
                                .padding(bottom = 32.dp) // mb-8
                        ) {
                            Text(
                                text = "> ${currentStep.code}...\n> STATUS: STANDBY",
                                style = EsFineTypography.labelSmall, // text-[10px]
                                color = TextCharcoal.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
            
            // Footer Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                // Step indicators
                Row(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp) // gap-2
                ) {
                    steps.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(8.dp) // w-2 h-2
                                .clip(CircleShape)
                                .background(
                                    if (index == onboardingState.currentStep) {
                                        TextCharcoal
                                    } else {
                                        BackgroundSecondary
                                    }
                                )
                        )
                    }
                }
                
                // Next/Initialize button
                TextButton(
                    onClick = {
                        if (onboardingState.currentStep < steps.size - 1) {
                            viewModel.onboardingNext()
                        } else {
                            viewModel.onboardingComplete()
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(50)) // rounded-full
                        .background(TextCharcoal) // bg-[#333333]
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp) // gap-3
                    ) {
                        Text(
                            text = if (onboardingState.currentStep == steps.size - 1) "INITIALIZE" else "NEXT",
                            style = EsFineTypography.bodySmall, // text-xs
                            color = CardWhite
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp) // w-8 h-8
                                .clip(CircleShape)
                                .background(CardWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (onboardingState.currentStep == steps.size - 1) {
                                    Icons.Default.Check
                                } else {
                                    Icons.Default.ChevronRight
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = TextCharcoal
                            )
                        }
                    }
                }
            }
        }
    }
}
