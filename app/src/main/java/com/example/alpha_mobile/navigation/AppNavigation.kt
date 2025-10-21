package com.example.alpha_mobile.navigation

import RegistroScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alpha_mobile.view.ResumenScreen
import com.example.alpha_mobile.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val usuarioViewModel : UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "registro"
    ) {
        composable("registro"){
            RegistroScreen(navController, usuarioViewModel)
        }

        composable("resumen"){
            ResumenScreen(usuarioViewModel)
        }
    }

}