package com.example.alpha_mobile.views

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts



@Composable
fun ConfiguracionScreen(navController: NavController) {

    //Estados locales para los switches
    var notificaciones by remember { mutableStateOf(true) }
    var recomendaciones by remember { mutableStateOf(true) }

    //Contenedor principal de la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            //Título
            Text(
                text = "Configuración",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF111827),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Seccion -> Notificaciones
            Text(
                text = "Notificaciones",
                color = Color(0xFF6B7280),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            //Card con switches de preferencias
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
            ) {
                Column(Modifier.padding(16.dp)) {
                    AjusteSwitch(
                        titulo = "Recibir notificaciones",
                        checked = notificaciones,
                        onCheckedChange = { notificaciones = it }
                    )
                    AjusteSwitch(
                        titulo = "Sugerencias personalizadas",
                        checked = recomendaciones,
                        onCheckedChange = { recomendaciones = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Sección -> Ayuda y soporte
            Text(
                text = "Ayuda y soporte",
                color = Color(0xFF6B7280),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
            ) {
                Column(Modifier.padding(16.dp)) {
                    AjusteItem(icono = Icons.Default.SupportAgent, titulo = "Centro de ayuda")
                    AjusteItem(icono = Icons.Default.Info, titulo = "Términos y condiciones")
                }
            }

            //Sección -> Funciones del dispositivo (Solo ubicación por ahora)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Funciones del dispositivo",
                color = Color(0xFF6B7280),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
            ) {
                Column(Modifier.padding(16.dp)) {
                    AjusteItemUbicacion()
                }
            }

            //Version de la aplicación
            Text(
                text = "Versión 1.0.0",
                color = Color(0xFF9CA3AF),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

//Componentes

//Item de texto simple (soporte o términos)
@Composable
fun AjusteItem(icono: androidx.compose.ui.graphics.vector.ImageVector, titulo: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Color(0xFF2563EB),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = titulo,
            color = Color(0xFF111827),
            fontSize = 16.sp
        )
    }
}

//Switch genérico para activar o desactivar opciones
@Composable
fun AjusteSwitch(
    titulo: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            color = Color(0xFF111827),
            fontSize = 16.sp
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

//Funcion nativa: Obtener ubicacion
@Composable
fun AjusteItemUbicacion() {
    val context = LocalContext.current
    var ubicacion by remember { mutableStateOf("Ubicación no disponible") }

    //Cliente de ubicación de Google Play Services
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    //ventana para pedir permiso de ubicación al usuario
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Si el permiso fue concedido, se obtiene la ubicacion
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    ubicacion =
                        "Lat: ${"%.4f".format(location.latitude)}, Lon: ${"%.4f".format(location.longitude)}"
                } else {
                    ubicacion = "No se pudo obtener la ubicación"
                }
            }
        } else {
            ubicacion = "Permiso de ubicación denegado"
        }
    }

    Column {
        //Muestra la información de la ubicacion
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Obtener ubicación actual",
                    color = Color(0xFF111827),
                    fontSize = 16.sp
                )
                Text(
                    text = ubicacion,
                    color = Color(0xFF6B7280),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                //Si el permiso ya fue concedido, se obtiene la ubicacion altiro
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            ubicacion =
                                "Lat: ${"%.4f".format(location.latitude)}, Lon: ${"%.4f".format(location.longitude)}"
                        } else {
                            ubicacion = "No se pudo obtener la ubicación"
                        }
                    }
                } else {
                    //Si no tiene permisos, se lanza el coso para solicitarlos
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
        ) {
            Text("Mostrar ubicación", color = Color.White)
        }
    }
}