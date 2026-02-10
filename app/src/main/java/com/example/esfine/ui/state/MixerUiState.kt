package com.example.esfine.ui.state

data class MixerUiState(
    val isPlaying: Boolean = false,
    val showMoodScanner: Boolean = false,
    val textureLevel: Int = 60,
    val melodyLevel: Int = 40,
    val activePreset: String? = null,
    val randomizeNonce: Int = 0
)
