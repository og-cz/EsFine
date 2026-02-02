package com.example.espin.ui.components

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
import com.example.espin.ui.theme.AccentSage
import com.example.espin.ui.theme.BorderMuted
import com.example.espin.ui.theme.CardWhite
import com.example.espin.ui.theme.EspinTypography
import com.example.espin.ui.theme.HoverLightGreen
import com.example.espin.ui.theme.TextCharcoal
import com.example.espin.ui.theme.TextMuted

/**
 * Mood button component matching the button structure from MoodScanner.tsx
 * Matches: className="flex flex-col p-4 bg-white border border-[#E0E0E0] rounded-2xl..."
 */
@Composable
fun MoodButton(
    label: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight() // Allow it to fit the icon + text
            .clip(RoundedCornerShape(16.dp))
            .background(CardWhite)
            .border(
                border = BorderStroke(1.dp, BorderMuted),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Icon container: w-10 h-10 -> 40dp
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp)) // rounded-xl
                    .background(Color(0xFFF5F5F5)), // bg-[#F5F5F5]
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(20.dp), // size={20}
                    tint = TextCharcoal
                )
            }
            
            // Label: font-bold text-sm -> 14sp bold
            Text(
                text = label,
                style = EspinTypography.bodyMedium,
                color = TextCharcoal,
                modifier = Modifier.padding(top = 12.dp) // mb-3 equivalent
            )
            
            // Description: text-[10px] -> 10sp
            Text(
                text = description,
                style = EspinTypography.labelSmall,
                color = TextMuted,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}
