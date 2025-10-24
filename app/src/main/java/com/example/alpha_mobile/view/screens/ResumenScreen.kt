package com.example.alpha_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResumenScreen(navController: NavController) {

    //Datos que se guardaron en el RegistroScreen
    val saved = navController.previousBackStackEntry?.savedStateHandle
    val nombre   = saved?.get<String>("nombre") ?: ""
    val correo   = saved?.get<String>("correo") ?: ""
    val claveLen = saved?.get<Int>("claveLen") ?: 0
    val acepta   = saved?.get<Boolean>("acepta") ?: false

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Resumen del Registro", style = MaterialTheme.typography.headlineMedium)

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Nombre: $nombre")
                Text("Correo: $correo")
                Text("Contraseña: ${"*".repeat(claveLen)}")
                Text("Términos: ${if (acepta) "Aceptados" else "No aceptados"}")
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}
