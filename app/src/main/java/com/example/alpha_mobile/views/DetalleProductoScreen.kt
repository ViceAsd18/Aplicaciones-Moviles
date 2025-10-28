package com.example.alpha_mobile.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
fun DetalleProductoScreen(navController: NavController, id: Int) {

    //Lista de productos simulada (datos estáticos)
    val productos = listOf(
        Producto(1, "PlayStation 5", "$649.990", R.drawable.ps5),
        Producto(2, "Xbox Series X", "$599.990", R.drawable.xbox),
        Producto(3, "Nintendo Switch OLED", "$389.990", R.drawable.nintendo),
        Producto(4, "DualSense Controller", "$89.990", R.drawable.mando),
        Producto(5, "Headset Pulse 3D", "$129.990", R.drawable.audifonos)
    )

    //Se busca el producto según el ID recibido
    val producto = productos.find { it.id == id }

    //Contenedor general de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            //Encabezado con el btn volver
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                //Icono para volver a la pantalla anterior
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.popBackStack() },
                    tint = Color(0xFF2563EB)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Detalle del producto",
                    color = Color(0xFF111827),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))


            if (producto != null) {
                //Si el producto existe, se muestra en la pantalla
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //Imagen del producto
                        Image(
                            painter = painterResource(id = producto.imagen),
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(12.dp))

                        //Nombre del producto
                        Text(
                            text = producto.nombre,
                            color = Color(0xFF111827),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(6.dp))

                        //Precio del producto
                        Text(
                            text = producto.precio,
                            color = Color(0xFF2563EB),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(16.dp))
                        //Descripción generica del producto
                        Text(
                            text = "Descripción del producto: Este artículo es parte de nuestro catálogo oficial de GameZone.",
                            color = Color(0xFF374151),
                            fontSize = 15.sp
                        )
                        Spacer(Modifier.height(24.dp))

                        //Boton para simular la compra
                        Button(
                            onClick = { /* No hace anda por ahora */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Comprar ahora", color = Color.White)
                        }
                    }
                }
            } else {
                //Si no se encuentra el producto, se muestra el siguiente texto en la pantalla
                Text(
                    text = "Producto no encontrado",
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 100.dp)
                )
            }
        }
    }
}
