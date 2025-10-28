package com.example.alpha_mobile.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.alpha_mobile.model.Usuario
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore(name = "preferencia_usuario")

// Sirve para guardar datos simples y persistentes (por ejemplo: sesión, nombre del usuario, etc.)
class EstadoDataStore(private val context: Context) {

    //Claves principales para identificar cada valor dentro del DataStore
    private val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    private val SESION_ACTIVA = booleanPreferencesKey("sesion_activa")
    private val USUARIOS = stringPreferencesKey("usuarios_registrados")

    //Guardar sesión
    suspend fun guardarUsuario(nombre: String, sesionActiva: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[NOMBRE_USUARIO] = nombre
            prefs[SESION_ACTIVA] = sesionActiva
        }
    }

    fun obtenerNombre(): Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[NOMBRE_USUARIO] }

    fun obtenerSesionActiva(): Flow<Boolean> =
        context.dataStore.data.map { prefs -> prefs[SESION_ACTIVA] ?: false }

    //Cierra sesión eliminando el nombre del usuario y marcando la sesión como inactiva.
    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(NOMBRE_USUARIO)
            prefs[SESION_ACTIVA] = false
        }
    }

    //Guarda una Lista para todos los usuarios registrados
    suspend fun guardarUsuarios(lista: List<Usuario>) {
        val json = Json.encodeToString(lista)
        context.dataStore.edit { prefs ->
            prefs[USUARIOS] = json
        }
    }

    //Recupera la lista de usuarios guardados desde JSON y la convierte a una lista de objetos Usuario.
    fun obtenerUsuarios(): Flow<List<Usuario>> =
        context.dataStore.data.map { prefs ->
            val json = prefs[USUARIOS]
            if (json != null) Json.decodeFromString(json) else emptyList()
        }
}
