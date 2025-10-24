package com.example.alpha_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alpha_mobile.data.EstadoDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val scope = rememberCoroutineScope()

    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var cargando by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    //Si ya hay sesión activa, ir directo al home
    LaunchedEffect(Unit) {
        dataStore.obtenerSesionActiva().collectLatest { activa ->
            if (activa) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
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

            Text("Iniciar sesión en GameZone", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo @duoc.cl") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = clave,
                onValueChange = { clave = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            //Mensaje de error
            error?.let {
                Text(
                    it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
            }

            //Botón principal
            Button(
                onClick = {
                    error = null

                    // Validaciones básicas
                    if (correo.isBlank() || clave.isBlank()) {
                        error = "Completa correo y contraseña"
                        return@Button
                    }
                    if (!correo.matches(Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$"))) {
                        error = "Debe ser un correo válido @duoc.cl"
                        return@Button
                    }
                    if (clave.length < 10) {
                        error = "La contraseña debe tener al menos 10 caracteres"
                        return@Button
                    }

                    cargando = true
                    scope.launch {
                        // ⚙️ Simulación de autenticación local (sin backend)
                        kotlinx.coroutines.delay(400)
                        dataStore.guardarUsuario(correo.trim(), true)
                        cargando = false

                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !cargando
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
