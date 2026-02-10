package com.example.esfine.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val EsFineFont = FontFamily.Default

private val LabelSmall = TextStyle(
    fontSize = 11.sp,
    fontFamily = EsFineFont,
    fontWeight = FontWeight.Medium,
    letterSpacing = 0.sp
)

private val BodySmall = TextStyle(
    fontSize = 13.sp,
    fontFamily = EsFineFont,
    fontWeight = FontWeight.Normal,
    letterSpacing = 0.sp
)

private val BodyMedium = TextStyle(
    fontSize = 15.sp,
    fontFamily = EsFineFont,
    fontWeight = FontWeight.Medium
)

private val TitleMedium = TextStyle(
    fontSize = 20.sp,
    fontFamily = EsFineFont,
    fontWeight = FontWeight.SemiBold
)

private val TitleLarge = TextStyle(
    fontSize = 26.sp,
    fontFamily = EsFineFont,
    fontWeight = FontWeight.SemiBold
)

val EsFineTypography = Typography(
    titleLarge = TitleLarge,
    titleMedium = TitleMedium,
    bodyMedium = BodyMedium,
    bodySmall = BodySmall,
    labelSmall = LabelSmall,
    labelMedium = LabelSmall,
    labelLarge = BodySmall
)
