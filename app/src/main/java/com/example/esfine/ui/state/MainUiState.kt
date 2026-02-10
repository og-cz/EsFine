package com.example.esfine.ui.state

data class MainUiState(
    val activeTab: AppTab = AppTab.Mixer,
    val hasOnboarded: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.LIGHT
)

enum class ThemeMode { LIGHT, DARK }

enum class AppTab {
    Mixer,
    Library,
    System
}
