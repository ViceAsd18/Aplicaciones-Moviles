package com.example.alpha_mobile.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpha_mobile.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    //Estados desde el ViewModel
    val correo by viewModel.correo.collectAsState()
    val clave by viewModel.clave.collectAsState()
    val error by viewModel.error.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val sesionActiva by viewModel.sesionActiva.collectAsState()

    //Navegar al tiro al Home si hay una sesión activa
    LaunchedEffect(sesionActiva) {
        if (sesionActiva) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Título principal
            Text("Iniciar sesión en GameZone", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            //Campo para el correo
            OutlinedTextField(
                value = correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo @duoc.cl") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(Modifier.height(8.dp))

            //Campo para la contraseña
            OutlinedTextField(
                value = clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Mensaje de error
            error?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
            }

            //Botón de inicio de sesion
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !cargando //se desactiva mientras carga
            ) {
                if (cargando) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Text("Iniciar sesión")
                }
            }

            Spacer(Modifier.height(16.dp))

            //Enlace para ir al registro
            TextButton(onClick = {
                navController.navigate("registro") {
                    popUpTo("login") { inclusive = false }
                }
            }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}
