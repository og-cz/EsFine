package com.example.esfine.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.esfine.navigation.EsFineNavigationSuite
import com.example.esfine.ui.state.AppTab
import com.example.esfine.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mainUiState by viewModel.mainUiState.collectAsState()

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
        EsFineNavigationSuite(viewModel = viewModel) { activeTab ->
            when (activeTab) {
                AppTab.Mixer -> CoreMixerScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                AppTab.Library -> SoundLibraryScreen(modifier = Modifier.fillMaxSize())
                AppTab.System -> SystemSettingsScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
            }
        }
    }
}
