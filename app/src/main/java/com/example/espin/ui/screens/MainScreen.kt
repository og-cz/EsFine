package com.example.espin.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.espin.navigation.EspinNavigationSuite
import com.example.espin.ui.state.AppTab
import com.example.espin.ui.viewmodel.MainViewModel

/**
 * Main screen composable - root of the app.
 * Matches the structure from App.tsx with conditional rendering.
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mainUiState by viewModel.mainUiState.collectAsState()
    
    // Conditional rendering: show onboarding if not completed, else show main content
    // Matches: {!hasOnboarded ? <TechnicalOnboarding /> : renderScreen()}
    AnimatedVisibility(
        visible = !mainUiState.hasOnboarded,
        modifier = modifier.fillMaxSize()
    ) {
        TechnicalOnboardingScreen(
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize()
        )
    }
    
    AnimatedVisibility(
        visible = mainUiState.hasOnboarded,
        modifier = modifier.fillMaxSize()
    ) {
        EspinNavigationSuite(viewModel = viewModel) { activeTab ->
            when (activeTab) {
                AppTab.Mixer -> CoreMixerScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
                AppTab.Library -> SoundLibraryScreen(
                    modifier = Modifier.fillMaxSize()
                )
                AppTab.System -> SystemSettingsScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
