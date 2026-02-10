package com.example.esfine.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.esfine.domain.model.MoodPreset
import com.example.esfine.ui.state.AppTab
import com.example.esfine.ui.state.MainUiState
import com.example.esfine.ui.state.MixerUiState
import com.example.esfine.ui.state.OnboardingUiState
import com.example.esfine.ui.state.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    // Main UI State
    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState: StateFlow<MainUiState> = _mainUiState.asStateFlow()

    // Mixer UI State
    private val _mixerUiState = MutableStateFlow(MixerUiState())
    val mixerUiState: StateFlow<MixerUiState> = _mixerUiState.asStateFlow()

    // Onboarding UI State
    private val _onboardingUiState = MutableStateFlow(OnboardingUiState())
    val onboardingUiState: StateFlow<OnboardingUiState> = _onboardingUiState.asStateFlow()

    // Main App Functions
    fun setActiveTab(tab: AppTab) {
        _mainUiState.update { it.copy(activeTab = tab) }
    }

    fun setHasOnboarded(completed: Boolean) {
        _mainUiState.update { it.copy(hasOnboarded = completed) }
    }

    // Theme
    fun setThemeMode(mode: ThemeMode) {
        _mainUiState.update { it.copy(themeMode = mode) }
    }

    fun toggleTheme() {
        _mainUiState.update {
            it.copy(themeMode = if (it.themeMode == ThemeMode.DARK) ThemeMode.LIGHT else ThemeMode.DARK)
        }
    }

    // Mixer Functions
    fun togglePlayback() {
        _mixerUiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun showMoodScanner(show: Boolean) {
        _mixerUiState.update { it.copy(showMoodScanner = show) }
    }

    fun applyPreset(preset: MoodPreset) {
        _mixerUiState.update {
            it.copy(
                textureLevel = preset.texture,
                melodyLevel = preset.melody,
                activePreset = preset.name,
                isPlaying = true,
                showMoodScanner = false
            )
        }
    }

    fun setTextureLevel(level: Int) {
        _mixerUiState.update { it.copy(textureLevel = level.coerceIn(0, 100)) }
    }

    fun setMelodyLevel(level: Int) {
        _mixerUiState.update { it.copy(melodyLevel = level.coerceIn(0, 100)) }
    }

    fun resetMixer() {
        _mixerUiState.update {
            it.copy(
                textureLevel = 60,
                melodyLevel = 40,
                activePreset = null,
                isPlaying = false
            )
        }
    }

    fun randomizeTracks() {
        _mixerUiState.update { it.copy(randomizeNonce = it.randomizeNonce + 1) }
    }

    // Onboarding Functions
    fun onboardingNext() {
        _onboardingUiState.update { state ->
            if (state.currentStep < OnboardingUiState.TOTAL_STEPS - 1) {
                state.copy(currentStep = state.currentStep + 1)
            } else {
                state.copy(isComplete = true)
            }
        }
    }

    fun onboardingComplete() {
        _onboardingUiState.update { it.copy(isComplete = true) }
        setHasOnboarded(true)
    }
}
