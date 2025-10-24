package com.example.alpha_mobile.view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.alpha_mobile.data.EstadoDataStore
import com.example.alpha_mobile.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var showText by remember { mutableStateOf(false) }
    var menuAbierto by remember { mutableStateOf(false) }

    // Cargar nombre al iniciar
    LaunchedEffect(Unit) {
        showText = true
        dataStore.obtenerNombre().collectLatest { guardado ->
            nombre = guardado ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GameZone") },
                actions = {
                    Box {
                        IconButton(onClick = { menuAbierto = !menuAbierto }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }

                        DropdownMenu(
                            expanded = menuAbierto,
                            onDismissRequest = { menuAbierto = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Logout,
                                        contentDescription = "Cerrar sesión"
                                    )
                                },
                                onClick = {
                                    menuAbierto = false
                                    scope.launch {
                                        dataStore.cerrarSesion()
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Home.route) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(visible = showText) {
                    Text(
                        text = "¡Bienvenido a GameZone!",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Explora tus juegos favoritos y descubre nuevos títulos.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
