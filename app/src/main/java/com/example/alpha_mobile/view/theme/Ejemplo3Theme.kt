package com.example.alpha_mobile.view.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val DefaultTypography = androidx.compose.material3.Typography()
val DefaultShapes = androidx.compose.material3.Shapes()

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