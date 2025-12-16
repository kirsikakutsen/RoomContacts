package com.example.room.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(
    primary = Blue8B,
    onPrimary = Black0D,
    primaryContainer = Blue1A,
    onPrimaryContainer = PureWhite,
    secondary = Purple91,
    onSecondary = PureWhite,
    secondaryContainer = Blue1F,
    onSecondaryContainer = PureWhite,
    tertiary = RedCE,
    onTertiary = PureWhite,
    background = Black0D,
    onBackground = PureWhite,
    surface = Blue1A,
    onSurface = PureWhite,
    surfaceVariant = Blue1F,
    onSurfaceVariant = GrayB0,
    outline = Gray6B,
)

@Composable
fun RoomTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Black0D.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
    
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
