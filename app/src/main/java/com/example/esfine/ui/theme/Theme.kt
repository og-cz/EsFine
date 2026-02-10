package com.example.esfine.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val EsFineLightColorScheme = lightColorScheme(
    primary = AccentSage,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,

    onPrimary = LightSurface,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,

    outline = LightOutline
)

private val EsFineDarkColorScheme = darkColorScheme(
    primary = AccentSage,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,

    onPrimary = DarkBackground,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,

    outline = DarkOutline
)

@Composable
fun EsFineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) EsFineDarkColorScheme else EsFineLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // Match status bar to background
            window.statusBarColor = colorScheme.background.toArgb()

            // Light icons for dark theme, dark icons for light theme
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = EsFineTypography,
        shapes = EsFineShapes,
        content = content
    )
}
