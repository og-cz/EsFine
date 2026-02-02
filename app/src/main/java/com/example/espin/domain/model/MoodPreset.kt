package com.example.espin.domain.model

/**
 * Represents an audio preset configuration.
 * Maps to the preset objects in MoodScanner.tsx
 */
data class MoodPreset(
    val name: String,
    val texture: Int,  // 0-100
    val melody: Int    // 0-100
) {
    companion object {
        // Presets extracted from MoodScanner.tsx
        val GROUNDING = MoodPreset(
            name = "GROUNDING",
            texture = 80,
            melody = 10
        )
        
        val RECHARGE = MoodPreset(
            name = "RECHARGE",
            texture = 20,
            melody = 90
        )
        
        val DEEP_WORK = MoodPreset(
            name = "DEEP_WORK",
            texture = 50,
            melody = 50
        )
        
        val RESET = MoodPreset(
            name = "RESET",
            texture = 90,
            melody = 0
        )
    }
}
