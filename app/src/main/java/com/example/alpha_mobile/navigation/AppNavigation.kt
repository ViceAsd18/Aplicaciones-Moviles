package com.example.alpha_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.alpha_mobile.views.BarraNavegacion
import com.example.alpha_mobile.views.DetalleProductoScreen
import com.example.alpha_mobile.views.LoginScreen
import com.example.alpha_mobile.views.RegistroScreen

@Composable
fun AppNavigation(navController: NavHostController) {

    //Los "navController" controla las rutas.
    NavHost(
        navController = navController,
        startDestination = "login" //indica la primera pantalla que se muestra al abrir la app.
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("registro") {
            RegistroScreen(navController)
        }
        composable("home") {
            BarraNavegacion(navController)
        }

        // Usa un argumento dinámico {id} para identificar el producto seleccionado.
        composable("detalle/{id}") { backStackEntry ->
            // Obtiene el parámetro "id" desde la ruta.
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            // Muestra el detalle del producto seleccionado.
            DetalleProductoScreen(navController, id)
        }

    }
}
