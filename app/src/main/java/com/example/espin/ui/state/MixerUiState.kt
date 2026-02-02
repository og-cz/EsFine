package com.example.espin.ui.state

import com.example.espin.domain.model.MoodPreset

/**
 * State for the CoreMixer screen.
 * Maps to useState hooks in CoreMixer.tsx
 */
data class MixerUiState(
    val isPlaying: Boolean = false,                    // useState(false)
    val showMoodScanner: Boolean = false,              // useState(false)
    val textureLevel: Int = 60,                        // useState(60)
    val melodyLevel: Int = 40,                         // useState(40)
    val activePreset: String? = null                  // useState<string | null>(null)
)
