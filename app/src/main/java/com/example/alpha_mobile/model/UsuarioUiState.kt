package com.example.alpha_mobile.model

data class UsuarioUiState (
    val nombre : String = "",
    val correo : String = "",
    val clave : String = "",
    val direccion : String = "",
    val aceptaTermino : Boolean = false,
    val errores : UsuarioErrores = UsuarioErrores()
)
