package com.example.esfine.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Shape mapping from React Tailwind classes
// rounded-xl -> 12.dp
// rounded-2xl -> 16.dp
// rounded-[2.5rem] -> 24.dp (approximately)

val EsFineShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),      // rounded-lg equivalent
    small = RoundedCornerShape(12.dp),          // rounded-xl
    medium = RoundedCornerShape(16.dp),         // rounded-2xl
    large = RoundedCornerShape(24.dp),          // rounded-[2.5rem]
    extraLarge = RoundedCornerShape(50)         // rounded-full
)
