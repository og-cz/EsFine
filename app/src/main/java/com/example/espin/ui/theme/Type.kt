package com.example.espin.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system monospace font as fallback - can be replaced with custom font file later
val EspinMono = FontFamily.Monospace

// Typography mapping from React classes
// text-[10px] -> 10.sp
val LabelSmall = TextStyle(
    fontSize = 10.sp,
    fontFamily = EspinMono,
    fontWeight = FontWeight.Bold,
    letterSpacing = 0.5.sp
)

// text-xs -> 12.sp
val BodySmall = TextStyle(
    fontSize = 12.sp,
    fontFamily = EspinMono,
    letterSpacing = 1.sp
)

// text-sm -> 14.sp
val BodyMedium = TextStyle(
    fontSize = 14.sp,
    fontFamily = EspinMono,
    fontWeight = FontWeight.Bold
)

// text-xl -> 20.sp
val TitleMedium = TextStyle(
    fontSize = 20.sp,
    fontFamily = EspinMono,
    fontWeight = FontWeight.Bold
)

// text-2xl -> 24.sp
val TitleLarge = TextStyle(
    fontSize = 24.sp,
    fontFamily = EspinMono,
    fontWeight = FontWeight.Light
)

val EspinTypography = Typography(
    displayLarge = TitleLarge,
    displayMedium = TitleMedium,
    displaySmall = TitleMedium,
    headlineLarge = TitleMedium,
    headlineMedium = BodyMedium,
    headlineSmall = BodyMedium,
    titleLarge = TitleMedium,
    titleMedium = BodyMedium,
    titleSmall = BodySmall,
    bodyLarge = BodyMedium,
    bodyMedium = BodySmall,
    bodySmall = LabelSmall,
    labelLarge = BodySmall,
    labelMedium = LabelSmall,
    labelSmall = LabelSmall
)
