package com.abdalla.altarifiappfinal.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
//    primary = paleBlack,
//    onPrimary = white,
//    primaryVariant = paleBlack,
//    background = paleBlack,
//    onBackground = white,

    primary = dark1,
    primaryVariant = dark1,
    background = dark1,
    secondary = dark2,
    surface = dark2,
    onPrimary = dark4,
    onBackground = dark4,
    onSecondary = dark4,
    onSurface = dark4
)

private val LightColorPalette = lightColors(
//    primary = paleWhite,
//    onPrimary = black,
//    primaryVariant = paleWhite,
//    background = paleWhite,
//    onBackground = black,

    primary = light1,
    primaryVariant = light1,
    background = light1,
    secondary = light2,
    surface = light2,
    onPrimary = light4,
    onBackground = light4,
    onSecondary = light4,
    onSurface = light4
)

@Composable
fun AlTarifiAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}