package com.kyant.pixelmusic.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers

private val DarkColorPalette = darkColors(
    primary = googleBlueDark,
    primaryVariant = googleBlue,
    secondary = androidGreen
)

private val LightColorPalette = lightColors(
    primary = googleBlue,
    primaryVariant = googleBlue,
    secondary = androidGreen
)

@Composable
fun PixelMusicTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = typography,
        shapes = shapes,
        content = {
            Providers(LocalContentColor provides MaterialTheme.colors.onSurface) {
                content()
            }
        }
    )
}