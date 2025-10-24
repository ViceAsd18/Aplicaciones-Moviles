package com.example.alpha_mobile.navigation

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Registro : Screen("registro")
    data object Home : Screen("home")

    data object Perfil : Screen("perfil")
    data object Configuracion : Screen("configuracion")
    data object Principal : Screen("principal")

    data class Detalle(val itemId: String) : Screen("detalle/{itemId}") {
        fun buildRoute(): String = "detalle/$itemId"
    }
}
