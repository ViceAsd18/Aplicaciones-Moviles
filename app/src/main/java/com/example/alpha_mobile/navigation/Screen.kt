package com.example.alpha_mobile.navigation

// Cada objeto representa una pantalla dentro de la app.
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Registro : Screen("registro")
    data object Home : Screen("home")
    data object Camera : Screen("camera")
}
