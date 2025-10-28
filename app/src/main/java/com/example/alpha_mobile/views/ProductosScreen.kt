package com.example.alpha_mobile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alpha_mobile.R
import com.example.alpha_mobile.model.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(navController: NavController) {

    //Lista de productos simulados
    val productos = listOf(
        Producto(1, "PlayStation 5", "$649.990", R.drawable.ps5),
        Producto(2, "Xbox Series X", "$599.990", R.drawable.xbox),
        Producto(3, "Nintendo Switch OLED", "$389.990", R.drawable.nintendo),
        Producto(4, "DualSense Controller", "$89.990", R.drawable.mando),
        Producto(5, "Headset Pulse 3D", "$129.990", R.drawable.audifonos)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Titulo de la pantalla
        Text(
            text = "Catálogo de Productos",
            color = Color(0xFF111827),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        //Listado vertical
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            //Muestra cada producto dentro de una Card
            items(productos) { producto ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    onClick = {
                        //Navega al detalle del producto cuando hacen click en la card
                        navController.navigate("detalle/${producto.id}")
                    }
                ) {
                    //Estructura interna de las Card
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Imagen del producto
                        Image(
                            painter = painterResource(id = producto.imagen),
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(end = 12.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            //Nombre del producto
                            Text(
                                text = producto.nombre,
                                color = Color(0xFF111827),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            // Precio
                            Text(
                                text = producto.precio,
                                color = Color(0xFF2563EB),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            //Estado de disponibilidad (Generico para todos)
                            Text(
                                text = "En stock",
                                color = Color(0xFF16A34A),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
