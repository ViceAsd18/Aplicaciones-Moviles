package com.example.alpha_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.alpha_mobile.data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }
    var generosSeleccionados by remember { mutableStateOf(setOf<String>()) }

    var error by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }
    var exito by remember { mutableStateOf(false) }

    val generos = listOf("FICCIÓN", "NO FICCIÓN", "MISTERIO", "TERROR", "SUSPENSO", "HISTORIA")

    fun validarFormulario(): Boolean {
        if (nombre.isBlank()) return error("El nombre no puede estar vacío").let { false }
        if (!nombre.matches(Regex("^[a-zA-ZÁÉÍÓÚáéíóúñÑ ]+$"))) return error("El nombre solo puede contener letras y espacios").let { false }
        if (nombre.length > 100) return error("El nombre no puede superar los 100 caracteres").let { false }

        if (correo.isBlank()) return error("El correo no puede estar vacío").let { false }
        if (!correo.matches(Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$"))) return error("Debe ser un correo válido @duoc.cl").let { false }
        if (correo.length > 60) return error("El correo no puede superar los 60 caracteres").let { false }

        if (clave.length < 10) return error("La contraseña debe tener al menos 10 caracteres").let { false }
        if (!clave.matches(Regex(".*[A-Z].*"))) return error("Debe tener al menos una mayúscula").let { false }
        if (!clave.matches(Regex(".*[a-z].*"))) return error("Debe tener al menos una minúscula").let { false }
        if (!clave.matches(Regex(".*\\d.*"))) return error("Debe tener al menos un número").let { false }
        if (!clave.matches(Regex(".*[@#\$%^&+=!¿?].*"))) return error("Debe tener al menos un carácter especial").let { false }

        if (clave != confirmar) return error("Las contraseñas no coinciden").let { false }

        if (telefono.isNotBlank() && !telefono.matches(Regex("^\\d{9,12}\$"))) return error("El teléfono debe ser numérico (9–12 dígitos)").let { false }

        if (generosSeleccionados.isEmpty()) return error("Selecciona al menos un género favorito").let { false }

        if (!aceptaTerminos) return error("Debes aceptar los términos y condiciones").let { false }

        return true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Crear cuenta GameZone", style = MaterialTheme.typography.titleLarge)

                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo @duoc.cl") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = clave, onValueChange = { clave = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = confirmar, onValueChange = { confirmar = it }, label = { Text("Confirmar contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono (opcional)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth(), singleLine = true)

                Text("Géneros favoritos", style = MaterialTheme.typography.titleMedium)
                generos.forEach { genero ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = generosSeleccionados.contains(genero),
                            onCheckedChange = {
                                generosSeleccionados =
                                    if (it) generosSeleccionados + genero else generosSeleccionados - genero
                            }
                        )
                        Text(genero)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = aceptaTerminos, onCheckedChange = { aceptaTerminos = it })
                    Text("Acepto los términos y condiciones")
                }

                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                if (exito) Text("Cuenta creada correctamente", color = MaterialTheme.colorScheme.primary)

                Button(
                    onClick = {
                        if (validarFormulario()) {
                            cargando = true
                            scope.launch {
                                try {
                                    delay(200)
                                    dataStore.guardarUsuario(nombre.trim(), true)
                                    cargando = false
                                    exito = true
                                    delay(800)
                                    navController.navigate("login") {
                                        popUpTo("registro") { inclusive = true }
                                    }
                                } catch (e: Exception) {
                                    cargando = false
                                    error = "Error al guardar usuario: ${e.localizedMessage ?: "Desconocido"}"
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !cargando
                ) {
                    if (cargando)
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    else
                        Text("Crear cuenta")
                }
            }
        }
    }
}
