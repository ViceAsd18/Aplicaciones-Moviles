package com.example.alpha_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alpha_mobile.view.screens.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Registro.route) { RegistroScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }

        composable(Screen.Perfil.route) { ProfileScreen(navController) }
        composable(Screen.Configuracion.route) { SettingsScreen(navController) }
        composable(Screen.Principal.route) { PantallaPrincipal(navController) }
    }
}
