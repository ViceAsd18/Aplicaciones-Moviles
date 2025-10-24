package com.example.alpha_mobile.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.alpha_mobile.R
import com.example.alpha_mobile.data.EstadoDataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PantallaPrincipal(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val scope = rememberCoroutineScope()

    // Estado local
    var activo by remember { mutableStateOf(false) }
    var mostrarMensaje by remember { mutableStateOf(false) }

    // Cargar estado guardado al iniciar
    LaunchedEffect(Unit) {
        dataStore.obtenerSesionActiva().collectLatest { guardado ->
            activo = guardado
        }
    }

    val colorAnimado by animateColorAsState(
        targetValue = if (activo) Color(0xFF4CAF50) else Color(0xFFB0BEC5),
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    val textoBoton by remember(activo) {
        derivedStateOf { if (activo) "Desactivar" else "Activar" }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_gamezone),
            contentDescription = "Logo GameZone",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Bienvenido a GameZone",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                scope.launch {
                    val nuevoValor = !activo
                    dataStore.guardarUsuario("UsuarioDemo", nuevoValor)
                    activo = nuevoValor
                    mostrarMensaje = true

                    kotlinx.coroutines.delay(1500)
                    mostrarMensaje = false
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(textoBoton, style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(visible = mostrarMensaje) {
            Text(
                text = "¡Estado guardado exitosamente!",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (activo) "Modo activo" else "Modo inactivo",
            color = if (activo) Color(0xFF4CAF50) else Color.Gray,
            fontSize = 18.sp
        )
    }
}
