package com.example.alpha_mobile.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraNavegacion(rootNavController: NavController) {

    val innerNavController = rememberNavController()

    // Índice seleccionado en la barra inferior.
    var selectedIndex by remember { mutableStateOf(0) }

    //Diseño base para el bottomBar y un slot para el contenido central.
    Scaffold(
        bottomBar = {
            // Barra inferior es igual en todas las pantallas hijas de este Scaffold.
            NavigationBar(containerColor = Color(0xFF1E1B4B)) {

                //Home
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                        innerNavController.navigate("home") { launchSingleTop = true }
                    },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio", tint = Color.White) },
                    label = { Text("Inicio", color = Color.White) }
                )

                //Productos
                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = {
                        selectedIndex = 1
                        innerNavController.navigate("productos") { launchSingleTop = true }
                    },
                    icon = { Icon(Icons.Filled.Store, contentDescription = "Productos", tint = Color.White) },
                    label = { Text("Productos", color = Color.White) }
                )

                //Pérfil
                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = {
                        selectedIndex = 2
                        innerNavController.navigate("perfil") { launchSingleTop = true }
                    },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil", tint = Color.White) },
                    label = { Text("Perfil", color = Color.White) }
                )

                //Configuracion
                NavigationBarItem(
                    selected = selectedIndex == 3,
                    onClick = {
                        selectedIndex = 3
                        innerNavController.navigate("configuracion") { launchSingleTop = true }
                    },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Ajustes", tint = Color.White) },
                    label = { Text("Ajustes", color = Color.White) }
                )
            }
        }
    ) { innerPadding ->
        //NavHost que renderiza el contenido según la ruta seleccionada.
        //El padding del Scaffold se pasa para que el contenido no quede debajo de la barra.
        NavHost(
            navController = innerNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Cada ruta interna monta su Composable correspondiente.
            // Se usa rootNavController para navegar a rutas.
            composable("home") { HomeScreen(rootNavController) }
            composable("productos") { ProductosScreen(rootNavController) }
            composable("perfil") { PerfilScreen(rootNavController) }
            composable("configuracion") { ConfiguracionScreen(rootNavController) }
        }
    }
}
