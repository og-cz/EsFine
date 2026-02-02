package com.example.espin.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a mood option in the MoodScanner.
 * Maps to the moods array in MoodScanner.tsx
 */
data class Mood(
    val id: String,
    val label: String,
    val icon: ImageVector,
    val description: String,
    val preset: MoodPreset
) {
    companion object {
        // Moods will be initialized with icons in the UI layer
        // These are the constants matching MoodScanner.tsx
        const val ID_SCATTERED = "anxious"
        const val ID_DRAINED = "tired"
        const val ID_FOCUSED = "focus"
        const val ID_OVERLOAD = "overwhelmed"
        
        const val LABEL_SCATTERED = "SCATTERED"
        const val LABEL_DRAINED = "DRAINED"
        const val LABEL_FOCUSED = "FOCUSED"
        const val LABEL_OVERLOAD = "OVERLOAD"
        
        const val DESC_SCATTERED = "Reduce noise. Increase stability."
        const val DESC_DRAINED = "Uplift energy. Binaural theta."
        const val DESC_FOCUSED = "Deep work. 40Hz Locked."
        const val DESC_OVERLOAD = "Total reset. Pink noise."
    }
}
