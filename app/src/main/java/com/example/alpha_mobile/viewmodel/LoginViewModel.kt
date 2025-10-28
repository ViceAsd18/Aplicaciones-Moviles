package com.example.alpha_mobile.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alpha_mobile.data.DB
import com.example.alpha_mobile.data.EstadoDataStore
import com.example.alpha_mobile.model.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Obtiene el DAO (Data Access Object) para interactuar con la tabla "usuarios".
    private val usuarioDao: UsuarioDao by lazy {
        DB.get(getApplication()).usuarioDao()
    }

    //Persistencia de sesion
    private val dataStore = EstadoDataStore(application.applicationContext)

    //Variables
    // Mantienen el estado actual del correo, clave, errores, etc. en la interfaz.
    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo

    private val _clave = MutableStateFlow("")
    val clave: StateFlow<String> = _clave

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _sesionActiva = MutableStateFlow(false)
    val sesionActiva: StateFlow<Boolean> = _sesionActiva

    init {
        //Verifica si hay una sesión activa en DataStore
        //Si existwe, al usuario se manda al home
        viewModelScope.launch {
            dataStore.obtenerSesionActiva().collect { activa ->
                _sesionActiva.value = activa
            }
        }
    }

    //Funciones para actualizar los campos del formulario
    fun onCorreoChange(nuevo: String) {
        _correo.value = nuevo
    }

    fun onClaveChange(nueva: String) {
        _clave.value = nueva
    }


    //Logica para logearse
    fun login() {
        _error.value = null
        val correoActual = _correo.value.trim()
        val claveActual = _clave.value

        //Validaciones
        if (correoActual.isBlank() || claveActual.isBlank()) {
            _error.value = "Completa correo y contraseña"
            return
        }

        if (!correoActual.matches(Regex("^[A-Za-z0-9._%+-]+@duoc\\.cl$"))) {
            _error.value = "Debe ser un correo válido @duoc.cl"
            return
        }

        if (claveActual.length < 10) {
            _error.value = "La contraseña debe tener al menos 10 caracteres"
            return
        }

        //Validar las credencias en la bd
        viewModelScope.launch {
            _cargando.value = true

            val usuario = usuarioDao.obtenerUsuarioPorCorreo(correoActual)

            //Controla los casos posibles
            when {
                usuario == null -> {
                    _error.value = "El correo no está registrado"
                }
                usuario.clave != claveActual -> {
                    _error.value = "Contraseña incorrecta"
                }
                else -> {
                    // Login correcto -> guardar sesión en DataStore
                    dataStore.guardarUsuario(usuario.correo, true)
                    _sesionActiva.value = true
                }
            }

            _cargando.value = false
        }
    }

    //Cierra la sesion
    fun logout() {
        viewModelScope.launch {
            // Borra los datos del usuario en DataStore y marca la sesión como cerrada.
            dataStore.cerrarSesion()
            _sesionActiva.value = false
        }
    }
}
