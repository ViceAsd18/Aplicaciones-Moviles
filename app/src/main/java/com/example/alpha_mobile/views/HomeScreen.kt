package com.example.alpha_mobile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.alpha_mobile.R
import com.example.alpha_mobile.viewmodel.LoginViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    //Estructura centrada
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Logo de la app (imagen circular)
        Image(
            painter = painterResource(id = R.drawable.logo_gamezone),
            contentDescription = "Logo GameZone",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Título principal
        Text(
            text = "Bienvenido a GameZone",
            color = Color(0xFF111827),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        //Subtitulo
        Text(
            text = "Tu espacio gamer en un solo lugar",
            color = Color(0xFF4B5563),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
