package com.example.alpha_mobile.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpha_mobile.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegistroViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val registroExitoso by viewModel.registroExitoso.collectAsState()

    // Navegar al login si el registro fue exitoso
    LaunchedEffect(registroExitoso) {
        if (registroExitoso) {
            navController.navigate("login") {
                popUpTo("registro") { inclusive = true }
            }
        }
    }

    val generos = listOf("FICCIÓN", "NO FICCIÓN", "MISTERIO", "TERROR", "SUSPENSO", "HISTORIA")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Título principal
            Text(
                "Crear cuenta GameZone",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(24.dp))

            //Campo: Nombr
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.errores.nombre?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            //Campo: Correo
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo @duoc.cl") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.errores.correo?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            //Campo: Contraseña
            OutlinedTextField(
                value = uiState.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.errores.clave?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            //Campo: Confirmar contraseña
            OutlinedTextField(
                value = uiState.confirmar,
                onValueChange = viewModel::onConfirmarChange,
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            uiState.errores.confirmar?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            //Campo: Telefono (opcional)
            OutlinedTextField(
                value = uiState.telefono,
                onValueChange = viewModel::onTelefonoChange,
                label = { Text("Teléfono (opcional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            //Seleccionar géneros
            Text("Géneros favoritos", style = MaterialTheme.typography.titleMedium)
            generos.forEach { genero ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = uiState.generos.contains(genero),
                        onCheckedChange = { viewModel.onGeneroChange(genero) }
                    )
                    Text(genero)
                }
            }

            uiState.errores.generos?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(8.dp))

            //Checkbox para aceptar los terminos
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.aceptaTermino,
                    onCheckedChange = viewModel::onAceptarTerminos
                )
                Text("Acepto los términos y condiciones")
            }

            Spacer(Modifier.height(16.dp))

            //Botón para crear la cuenta
            Button(
                onClick = { viewModel.registrarUsuario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Crear cuenta")
            }

            Spacer(Modifier.height(12.dp))

            //Enlace que dirige al login
            TextButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión aquí")
            }
        }
    }
}