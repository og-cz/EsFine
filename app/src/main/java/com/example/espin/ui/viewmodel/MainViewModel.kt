package com.example.espin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.espin.domain.model.MoodPreset
import com.example.espin.ui.state.AppTab
import com.example.espin.ui.state.MainUiState
import com.example.espin.ui.state.MixerUiState
import com.example.espin.ui.state.OnboardingUiState
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
    
    // Mixer Functions
    fun togglePlayback() {
        _mixerUiState.update { it.copy(isPlaying = !it.isPlaying) }
    }
    
    fun showMoodScanner(show: Boolean) {
        _mixerUiState.update { it.copy(showMoodScanner = show) }
    }
    
    fun applyPreset(preset: MoodPreset) {
        // Matches applyPreset logic from CoreMixer.tsx:
        // setTextureLevel(preset.texture)
        // setMelodyLevel(preset.melody)
        // setActivePreset(preset.name)
        // setIsPlaying(true)
        // setShowMoodScanner(false)
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
        // Matches reset logic from CoreMixer.tsx:
        // setTextureLevel(60)
        // setMelodyLevel(40)
        // setActivePreset(null)
        // setIsPlaying(false)
        _mixerUiState.update {
            it.copy(
                textureLevel = 60,
                melodyLevel = 40,
                activePreset = null,
                isPlaying = false
            )
        }
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
