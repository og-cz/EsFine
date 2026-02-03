package com.example.esfine.ui.components

import androidx.compose.runtime.remember
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.esfine.ui.theme.AccentSage
import com.example.esfine.ui.theme.BorderMuted
import com.example.esfine.ui.theme.CardWhite
import com.example.esfine.ui.theme.EsFineTypography
import com.example.esfine.ui.theme.HoverLightGreen
import com.example.esfine.ui.theme.TextCharcoal
import com.example.esfine.ui.theme.TextMuted

@Composable
fun MoodButton(
    label: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. Create the interaction source
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .border(
                border = BorderStroke(1.dp, BorderMuted),
                shape = RoundedCornerShape(16.dp)
            )
            // 2. Updated clickable block to support the new Ripple system
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onClick
            )
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(20.dp),
                    tint = TextCharcoal
                )
            }

            Text(
                text = label,
                style = EsFineTypography.bodyMedium,
                color = TextCharcoal,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = description,
                style = EsFineTypography.labelSmall,
                color = TextMuted,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}
