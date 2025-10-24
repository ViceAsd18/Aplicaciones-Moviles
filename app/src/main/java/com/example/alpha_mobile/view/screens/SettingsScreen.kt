package com.example.alpha_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alpha_mobile.data.EstadoDataStore
import com.example.alpha_mobile.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val scope = rememberCoroutineScope()

    var menuAbierto by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
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
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "Centro de Configuración",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Desde aquí podrás ajustar tus preferencias, notificaciones y temas visuales. " +
                            "Muy pronto podrás personalizar tu experiencia de usuario dentro de GameZone.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
