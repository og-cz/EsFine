package com.example.espin.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album // Changed from Disc
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Tune // Changed from Sliders
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.espin.ui.state.AppTab
/**
 * Navigation destinations matching App.tsx tabs
 */
sealed class AppDestination(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val tab: AppTab
) {
    data object Mixer : AppDestination(
        route = "mixer",
        icon = Icons.Default.Tune, // Matches the Mixer/Sliders look
        label = "MIXER",
        tab = AppTab.Mixer
    )

    data object Library : AppDestination(
        route = "library",
        icon = Icons.Default.Album, // Matches the Disc/Record look
        label = "LIBRARY",
        tab = AppTab.Library
    )

    data object System : AppDestination(
        route = "system",
        icon = Icons.Default.Radio,
        label = "SYSTEM",
        tab = AppTab.System
    )

    companion object {
        val allDestinations = listOf(Mixer, Library, System)
    }
}
