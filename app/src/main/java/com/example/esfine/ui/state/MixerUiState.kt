package com.example.esfine.ui.state

/**
 * State for the CoreMixer screen.
 */
data class MixerUiState(
    val isPlaying: Boolean = false,
    val showMoodScanner: Boolean = false,
    val textureLevel: Int = 60,
    val melodyLevel: Int = 40,
    val activePreset: String? = null,

    // NEW: increments whenever user taps refresh
    val randomizeNonce: Int = 0
)
