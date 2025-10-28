package com.example.alpha_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.alpha_mobile.navigation.AppNavigation
import com.example.alpha_mobile.ui.theme.AlphaMobileTheme

class MainActivity : ComponentActivity() {
    //Metodo que se ejecuta cuando la app se inicia.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Aplica el tema global de la app (colores, tipografía, etc).
            AlphaMobileTheme {
                //contenedor visual
                Surface {
                    // Crea el controlador de navegación que maneja las rutas entre pantallas.
                    val navController = rememberNavController()
                    // Llama a AppNavigation, que contiene todas las pantallas
                    // (Login, Registro, Home, Perfil, etc.) y sus rutas.
                    AppNavigation(navController)
                }
            }
        }
    }
}
