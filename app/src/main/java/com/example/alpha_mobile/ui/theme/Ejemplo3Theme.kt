package com.example.alpha_mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DefaultTypography = Typography()
val DefaultShapes = Shapes()

@Composable
fun Ejemplo3Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = DefaultTypography,
        shapes = DefaultShapes,
        content = content
    )
}