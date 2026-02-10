package com.example.esfine.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.EsFineTypography

data class SoundCard(
    val title: String,
    val tag: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val backgroundColor: Color
)

@Composable
fun SoundLibraryScreen(
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme

    val bg = scheme.background
    val surface = scheme.surface
    val surfaceVariant = scheme.surfaceVariant
    val onSurface = scheme.onSurface
    val outline = scheme.outline

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Sonic Architectures",
            style = EsFineTypography.titleLarge,
            color = onSurface,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Featured Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(onSurface) // strong contrast block
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentSage)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Recommended",
                            style = EsFineTypography.labelSmall.copy(fontSize = 9.sp),
                            color = scheme.onPrimary
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Deep Work\nProtocol",
                        style = EsFineTypography.titleMedium,
                        color = scheme.background,
                        fontWeight = FontWeight.Light
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "40Hz • Binaural",
                        style = EsFineTypography.bodySmall,
                        color = scheme.background.copy(alpha = 0.75f)
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            SectionHeader(
                title = "Lofi Layers",
                dotColor = onSurface,
                lineColor = outline.copy(alpha = 0.35f),
                textColor = onSurface
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard("Night Rain", "Texture", Icons.Default.WaterDrop, surfaceVariant),
                        SoundCard("Vinyl Crackle", "ASMR", Icons.Default.MusicNote, surfaceVariant)
                    )
                ) { card ->
                    SoundCardItem(
                        card = card,
                        onSurface = onSurface,
                        outline = outline
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(
                title = "Neural Frequencies",
                dotColor = onSurface,
                lineColor = outline.copy(alpha = 0.35f),
                textColor = onSurface
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    listOf(
                        SoundCard("Brown Noise", "Quiet", Icons.Default.Headphones, surfaceVariant),
                        SoundCard("Theta Waves", "Calm", Icons.Default.Bolt, surfaceVariant)
                    )
                ) { card ->
                    SoundCardItem(
                        card = card,
                        onSurface = onSurface,
                        outline = outline
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    dotColor: Color,
    lineColor: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(dotColor.copy(alpha = 0.85f))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = EsFineTypography.bodySmall,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
                .background(lineColor)
        )
    }
}

@Composable
private fun SoundCardItem(
    card: SoundCard,
    onSurface: Color,
    outline: Color
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(card.backgroundColor)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { /* TODO: play category */ }
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = card.icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = onSurface.copy(alpha = 0.75f)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, outline.copy(alpha = 0.35f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = card.tag,
                        style = EsFineTypography.labelSmall.copy(fontSize = 9.sp),
                        color = onSurface.copy(alpha = 0.85f)
                    )
                }
            }

            Text(
                text = card.title,
                style = EsFineTypography.bodyMedium,
                color = onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
