package com.example.espin.ui.state

/**
 * Main application state.
 * Maps to useState hooks in App.tsx
 */
data class MainUiState(
    val activeTab: AppTab = AppTab.Mixer,              // useState('mixer')
    val hasOnboarded: Boolean = false                  // useState(false)
)

enum class AppTab {
    Mixer,      // 'mixer'
    Library,    // 'library'
    System      // 'system'
}
