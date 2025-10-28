package com.example.alpha_mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha_mobile.data.DB
import com.example.alpha_mobile.model.Usuario
import com.example.alpha_mobile.model.UsuarioDao
import com.example.alpha_mobile.model.UsuarioUiState
import com.example.alpha_mobile.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Realiza validaciones de los campos, verifica duplicados y guarda los datos en Room (SQLite).
class RegistroViewModel(application: Application) : AndroidViewModel(application) {

    // Obtiene el DAO (Data Access Object) para interactuar con la tabla "usuarios".
    private val usuarioDao: UsuarioDao by lazy {
        DB.get(getApplication()).usuarioDao()
    }

    //Estado del formulario y del proceso
    private val _uiState = MutableStateFlow(UsuarioUiState()) //Estado actual del formulario
    val uiState: StateFlow<UsuarioUiState> = _uiState

    private val _registroExitoso = MutableStateFlow(false) // Indica si el registro fue exitoso
    val registroExitoso: StateFlow<Boolean> = _registroExitoso

    //Actualización de campos individuales
    fun onNombreChange(valor: String) = _uiState.update { it.copy(nombre = valor) }
    fun onCorreoChange(valor: String) = _uiState.update { it.copy(correo = valor) }
    fun onClaveChange(valor: String) = _uiState.update { it.copy(clave = valor) }
    fun onConfirmarChange(valor: String) = _uiState.update { it.copy(confirmar = valor) }
    fun onTelefonoChange(valor: String) = _uiState.update { it.copy(telefono = valor) }

    //Contola la seccion de géneros
    fun onGeneroChange(valor: String) {
        _uiState.update {
            val nuevoSet = if (it.generos.contains(valor)) {
                it.generos - valor
            } else {
                it.generos + valor
            }
            it.copy(generos = nuevoSet)
        }
    }

    //Controla si el usuario acepto los terminos o no
    fun onAceptarTerminos(valor: Boolean) = _uiState.update { it.copy(aceptaTermino = valor) }

    //Logica del registro
    fun registrarUsuario() {
        val user = _uiState.value
        var errores = UsuarioErrores()
        var tieneErrores = false

        //Nombre
        if (user.nombre.isBlank()) {
            errores = errores.copy(nombre = "Ingresa tu nombre completo")
            tieneErrores = true
        } else {
            if (!user.nombre.matches(Regex("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$"))) {
                errores = errores.copy(nombre = "Solo se permiten letras y espacios")
                tieneErrores = true
            }
            if (user.nombre.length > 100) {
                errores = errores.copy(nombre = "Máximo 100 caracteres")
                tieneErrores = true
            }
        }

        //Correo
        if (user.correo.isBlank()) {
            errores = errores.copy(correo = "Ingresa tu correo institucional")
            tieneErrores = true
        } else {
            if (!user.correo.matches(Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$"))) {
                errores = errores.copy(correo = "Correo inválido. Debe ser @duoc.cl")
                tieneErrores = true
            }
            if (user.correo.length > 60) {
                errores = errores.copy(correo = "Máximo 60 caracteres")
                tieneErrores = true
            }
        }

        //Contraseña
        if (user.clave.isBlank()) {
            errores = errores.copy(clave = "Ingresa una contraseña")
            tieneErrores = true
        } else {
            // Debe tener mayúscula, minúscula, número, carácter especial y mínimo 10 caracteres
            if (!user.clave.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%]).{10,}$"))) {
                errores = errores.copy(
                    clave = "Debe tener mayúscula, minúscula, número, carácter especial y mínimo 10 caracteres"
                )
                tieneErrores = true
            }
        }

        //Confirmar contraseña
        if (user.confirmar != user.clave) {
            errores = errores.copy(confirmar = "Las contraseñas no coinciden")
            tieneErrores = true
        }

        //Teléfono (opcional)
        if (!user.telefono.isNullOrBlank() && !user.telefono.matches(Regex("^\\d{9}$"))) {
            errores = errores.copy(telefono = "Teléfono inválido (9 dígitos)")
            tieneErrores = true
        }

        //Géneros favoritos
        if (user.generos.isEmpty()) {
            errores = errores.copy(generos = "Selecciona al menos un género")
            tieneErrores = true
        }

        //Aceptar términos
        if (!user.aceptaTermino) {
            errores = errores.copy(generos = "Debes aceptar los términos y condiciones")
            tieneErrores = true
        }

        // Si hay errores, los muestra y no continúa
        if (tieneErrores) {
            _uiState.update { it.copy(errores = errores) }
            return
        }

        //Verificar duplicados en el Room
        viewModelScope.launch {
            val usuarioExistente = usuarioDao.obtenerUsuarioPorCorreo(user.correo)
            if (usuarioExistente != null) {
                errores = errores.copy(correo = "El usuario ya está registrado")
                _uiState.update { it.copy(errores = errores) }
                return@launch
            }

            //Registro exitoso
            val nuevoUsuario = Usuario(
                nombre = user.nombre.trim(),
                correo = user.correo.trim().lowercase(),
                clave = user.clave.trim(),
                telefono = user.telefono?.trim(),
                generos = user.generos.joinToString(","), //Convierte la lista a texto
                aceptaTerminos = user.aceptaTermino
            )

            // Inserta el nuevo usuario en la base de datos
            usuarioDao.insertarUsuario(nuevoUsuario)
            println("Usuario registrado en Room: $nuevoUsuario")

            // Limpia los campos y notifica que el registro fue exitoso
            _registroExitoso.value = true
            _uiState.update { UsuarioUiState() }
        }
    }
}
