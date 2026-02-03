package com.example.esfine.ui.screens

import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.BackgroundSecondary
import com.example.esfine.ui.theme.BorderMuted
import com.example.esfine.ui.theme.CardWhite
import com.example.esfine.ui.theme.EsFineTypography
import com.example.esfine.ui.theme.TextCharcoal
import com.example.esfine.ui.theme.TextMuted

data class SoundCard(
    val title: String,
    val tag: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val backgroundColor: Color
)

/**
 * SoundLibrary screen matching SoundLibrary.tsx
 */
@Composable
fun SoundLibraryScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "SONIC\nARCHITECTURES",
            style = EsFineTypography.titleLarge,
            color = TextCharcoal,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Featured Card logic...
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(TextCharcoal)
                .padding(24.dp)
                .padding(bottom = 32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentSage)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "RECOMMENDED",
                            style = EsFineTypography.labelSmall.copy(fontSize = 9.sp),
                            color = CardWhite
                        )
                    }
                    Text(
                        text = "Deep Work\nProtocol",
                        style = EsFineTypography.titleMedium,
                        color = CardWhite,
                        fontWeight = FontWeight.Light
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "40HZ • BINAURAL",
                        style = EsFineTypography.bodySmall,
                        color = TextMuted
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(CardWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = TextCharcoal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            SectionHeader(title = "LOFI LAYERS")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard("Night Rain", "TEXTURE", Icons.Default.WaterDrop, BackgroundSecondary),
                        SoundCard("Vinyl Crackle", "ASMR", Icons.Default.MusicNote, Color(0xFFE8E8E8))
                    )
                ) { card -> SoundCardItem(card = card) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "NEURAL FREQUENCIES")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard("Brown Noise", "QUIET", Icons.Default.Headphones, Color(0xFFD6D6D6)),
                        SoundCard("Theta Waves", "CALM", Icons.Default.Bolt, Color(0xFFCCCCCC))
                    )
                ) { card -> SoundCardItem(card = card) }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    // ... (Keep the SectionHeader code the same)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(TextCharcoal))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = title, style = EsFineTypography.bodySmall, color = TextCharcoal, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier.height(1.dp).width(1.dp).background(BackgroundSecondary))
    }
}

@Composable
private fun SoundCardItem(card: SoundCard) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp.coerceAtLeast(100.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(card.backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                // CHANGE THIS: from ripple() to rememberRipple()
                indication = rememberRipple(),
                onClick = {}
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = card.icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = TextCharcoal.copy(alpha = 0.7f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, TextCharcoal.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = card.tag,
                        style = EsFineTypography.labelSmall.copy(fontSize = 9.sp),
                        color = TextCharcoal
                    )
                }
            }
            Text(
                text = card.title,
                style = EsFineTypography.bodyMedium,
                color = TextCharcoal,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
