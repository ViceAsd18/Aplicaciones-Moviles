package com.example.alpha_mobile.view.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.alpha_mobile.navigation.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(Screen.Home, Screen.Perfil, Screen.Configuracion)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    when (screen) {
                        Screen.Home -> Icon(Icons.Default.Home, contentDescription = "Home")
                        Screen.Perfil -> Icon(Icons.Default.Person, contentDescription = "Perfil")
                        Screen.Configuracion -> Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                        else -> {}
                    }
                },
                label = {
                    when (screen) {
                        Screen.Home -> Text("Home")
                        Screen.Perfil -> Text("Perfil")
                        Screen.Configuracion -> Text("Ajustes")
                        else -> {}
                    }
                }
            )
        }
    }
}
