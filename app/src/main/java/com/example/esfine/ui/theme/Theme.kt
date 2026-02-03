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
    onPrimary = CardWhite,
    secondary = BackgroundSecondary,
    onSecondary = TextCharcoal,
    tertiary = AccentSage,
    onTertiary = CardWhite,
    background = BackgroundOffWhite,
    onBackground = TextCharcoal,
    surface = CardWhite,
    onSurface = TextCharcoal,
    surfaceVariant = BackgroundSecondary,
    onSurfaceVariant = TextSecondary,
    error = TextDark,
    onError = CardWhite
)

@Composable
fun EsFineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = EsFineLightColorScheme
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = EsFineTypography,
        shapes = EsFineShapes,
        content = content
    )
}
