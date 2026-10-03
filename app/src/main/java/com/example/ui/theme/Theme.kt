package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SinkiColorScheme = lightColorScheme(
    primary = GoldAccent,
    onPrimary = TextPrimary,
    primaryContainer = ChampagneHighlight,
    onPrimaryContainer = TextPrimary,
    secondary = TextSecondary,
    onSecondary = ChampagneHighlight,
    secondaryContainer = ChampagneSurface,
    onSecondaryContainer = TextPrimary,
    tertiary = GoldLight,
    onTertiary = TextPrimary,
    background = ChampagneMain,
    onBackground = TextPrimary,
    surface = ChampagneHighlight,
    onSurface = TextPrimary,
    surfaceVariant = ChampagneSurface,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    outlineVariant = BorderSoft
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SinkiColorScheme,
        typography = SinkiTypography,
        content = content
    )
}
