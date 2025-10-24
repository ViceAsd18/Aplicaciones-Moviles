package com.example.alpha_mobile.model

data class UsuarioUiState (
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmar: String = "",
    val telefono: String = "",
    val generos: Set<String> = emptySet(),
    val aceptaTermino: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)
