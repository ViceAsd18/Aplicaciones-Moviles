package com.example.alpha_mobile.views

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpha_mobile.data.EstadoDataStore
import com.example.alpha_mobile.viewmodel.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    //Dependencias principales
    val context = LocalContext.current
    val dataStore = remember { EstadoDataStore(context) }
    val scope = rememberCoroutineScope()

    // Control de sesión y nombre del usuario
    var nombre by remember { mutableStateOf<String?>(null) }
    val sesionActiva by viewModel.sesionActiva.collectAsState()

    // Variables para simular la toma de una foto
    var foto by remember { mutableStateOf<Bitmap?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    // Abre la cámara y simula el procesando la carga de imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                scope.launch {
                    isUploading = true      //Activa el estado de carga
                    delay(2000)  //Simula el procesamiento de la foto
                    foto = bitmap           //Muestra la imagen capturada
                    isUploading = false     //Finaliza la carga
                }
            }
        }
    )

    // Obtiene el nombre del usuario almacenado
    LaunchedEffect(Unit) {
        dataStore.obtenerNombre().collect { guardado ->
            nombre = guardado
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                //Foto de perfil / Cámara
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .border(3.dp, Color(0xFF2563EB), CircleShape)
                        .background(Color.White)
                        .clickable(enabled = !isUploading) { launcher.launch() },
                    contentAlignment = Alignment.Center
                ) {
                    if (foto != null) {
                        //Si el usuario toma una foto, se muestra dentro del círculo
                        Image(
                            bitmap = foto!!.asImageBitmap(),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        //Si no hay foto, se muestra el ícono de cámara
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Tomar foto",
                            tint = Color(0xFF1E1B4B),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }

                //Texto bajo el ícono
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Foto de perfil",
                    color = Color(0xFF6B7280),
                    fontSize = 14.sp
                )

                //Estado de la sesión
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sesión activa" ,
                    color = Color(0xFF16A34A),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                //Boton para cerrar la sesion
                Button(
                    onClick = {
                        scope.launch {
                            dataStore.cerrarSesion()
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", color = Color.White)
                }
            }

            //Animacion “Procesando foto...”
            AnimatedVisibility(
                visible = isUploading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(Modifier.height(12.dp))
                        Text("Procesando foto...", color = Color.White)
                    }
                }
            }
        }
    }
}