package com.example.alpha_mobile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alpha_mobile.model.UsuarioErrores
import com.example.alpha_mobile.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())

    val estado : StateFlow<UsuarioUiState> = _estado

    fun onNombreChange(valor : String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null))}
    }

    fun onCorreoChange(valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(correo = null))}
    }

    fun onClaveChange(valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null))}
    }

    fun onDireccionChage(valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onAceptarTerminosChange(valor : Boolean) {
        _estado.update { it.copy(aceptaTermino = valor)}
    }

    //Validaciones de formulario
    fun validarFormulario() : Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores (
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo invalido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo Obligartorio" else null
        )

        val HayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update {  it.copy(errores = errores)}

        return !HayErrores
    }



}