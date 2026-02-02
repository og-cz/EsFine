package com.example.espin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.espin.ui.state.AppTab
import com.example.espin.ui.viewmodel.MainViewModel

/**
 * Navigation scaffold with bottom navigation bar.
 * Matches the navigation structure from App.tsx
 * Note: Using standard bottom navigation for now - can be upgraded to adaptive NavigationSuite later
 */
@Composable
fun EspinNavigationSuite(
    viewModel: MainViewModel,
    content: @Composable (AppTab) -> Unit
) {
    val mainUiState by viewModel.mainUiState.collectAsState()
    val currentTab = mainUiState.activeTab
    
    // Map AppTab to index for NavigationBar
    var selectedIndex by remember { 
        mutableIntStateOf(
            when (currentTab) {
                AppTab.Mixer -> 0
                AppTab.Library -> 1
                AppTab.System -> 2
            }
        )
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                AppDestination.allDestinations.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            val tab = when (index) {
                                0 -> AppTab.Mixer
                                1 -> AppTab.Library
                                2 -> AppTab.System
                                else -> AppTab.Mixer
                            }
                            viewModel.setActiveTab(tab)
                        }
                    )
                }
            }
        }
        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content(currentTab)
        }
    }
}
