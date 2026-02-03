package com.example.esfine.ui.state

/**
 * State for the TechnicalOnboarding screen.
 * Maps to useState(0) in TechnicalOnboarding.tsx
 */
data class OnboardingUiState(
    val currentStep: Int = 0,                          // useState(0)
    val isComplete: Boolean = false
) {
    companion object {
        const val TOTAL_STEPS = 3
    }
}
