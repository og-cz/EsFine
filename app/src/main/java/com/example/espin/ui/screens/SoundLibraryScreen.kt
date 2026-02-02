package com.example.espin.ui.screens

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
import com.example.espin.ui.theme.AccentSage
import com.example.espin.ui.theme.BackgroundSecondary
import com.example.espin.ui.theme.BorderMuted
import com.example.espin.ui.theme.CardWhite
import com.example.espin.ui.theme.EspinTypography
import com.example.espin.ui.theme.TextCharcoal
import com.example.espin.ui.theme.TextMuted

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
            .padding(horizontal = 24.dp, vertical = 32.dp) // px-6 pt-8 pb-20
    ) {
        // Header
        Text(
            text = "SONIC\nARCHITECTURES",
            style = EspinTypography.titleLarge, // text-2xl
            color = TextCharcoal,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 24.dp) // mb-6
        )
        
        // Featured Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp) // h-48
                .clip(RoundedCornerShape(16.dp)) // rounded-2xl
                .background(TextCharcoal) // bg-[#333333]
                .padding(24.dp) // p-6
                .padding(bottom = 32.dp) // mb-8
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
            ) {
                Column {
                    // Recommended badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentSage) // bg-[#87A96B]
                            .padding(horizontal = 8.dp, vertical = 4.dp) // px-2 py-1
                            .padding(bottom = 8.dp) // mb-2
                    ) {
                        Text(
                            text = "RECOMMENDED",
                            style = EspinTypography.labelSmall.copy(fontSize = 9.sp), // text-[9px]
                            color = CardWhite
                        )
                    }
                    Text(
                        text = "Deep Work\nProtocol",
                        style = EspinTypography.titleMedium, // text-xl
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
                        style = EspinTypography.bodySmall, // text-xs
                        color = TextMuted
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp) // w-10 h-10
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
        
        Spacer(modifier = Modifier.height(24.dp)) // space-y-6
        
        // Categories
        Column(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp) // space-y-6
        ) {
            // Section: LOFI LAYERS
            SectionHeader(title = "LOFI LAYERS")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp), // gap-4
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard(
                            title = "Night Rain",
                            tag = "TEXTURE",
                            icon = Icons.Default.WaterDrop, // CloudRain equivalent
                            backgroundColor = BackgroundSecondary // bg-[#E0E0E0]
                        ),
                        SoundCard(
                            title = "Vinyl Crackle",
                            tag = "ASMR",
                            icon = Icons.Default.MusicNote, // Music
                            backgroundColor = Color(0xFFE8E8E8) // bg-[#E8E8E8]
                        )
                    )
                ) { card ->
                    SoundCardItem(card = card)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Section: NEURAL FREQUENCIES
            SectionHeader(title = "NEURAL FREQUENCIES")
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp), // gap-4
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard(
                            title = "Brown Noise",
                            tag = "QUIET",
                            icon = Icons.Default.Headphones,
                            backgroundColor = Color(0xFFD6D6D6) // bg-[#D6D6D6]
                        ),
                        SoundCard(
                            title = "Theta Waves",
                            tag = "CALM",
                            icon = Icons.Default.Bolt, // Zap
                            backgroundColor = Color(0xFFCCCCCC) // bg-[#CCCCCC]
                        )
                    )
                ) { card ->
                    SoundCardItem(card = card)
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp) // w-2 h-2
                .clip(CircleShape)
                .background(TextCharcoal)
        )
        Spacer(modifier = Modifier.width(12.dp)) // gap-3
        Text(
            text = title,
            style = EspinTypography.bodySmall, // text-xs
            color = TextCharcoal,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .height(1.dp) // h-[1px]
                .width(1.dp)
                .background(BackgroundSecondary) // bg-[#E0E0E0]
        )
    }
}

@Composable
private fun SoundCardItem(card: SoundCard) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp.coerceAtLeast(100.dp)) // min-h-[100px]
            .clip(RoundedCornerShape(12.dp)) // rounded-xl
            .background(card.backgroundColor)
            .clickable(onClick = {})
            .padding(16.dp) // p-4
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
                        .padding(horizontal = 4.dp, vertical = 2.dp) // px-1
                ) {
                    Text(
                        text = card.tag,
                        style = EspinTypography.labelSmall.copy(fontSize = 9.sp), // text-[9px]
                        color = TextCharcoal
                    )
                }
            }
            Text(
                text = card.title,
                style = EspinTypography.bodyMedium, // text-sm
                color = TextCharcoal,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
