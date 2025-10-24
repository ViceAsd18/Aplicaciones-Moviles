package com.example.alpha_mobile.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferencia_usuario")

class EstadoDataStore(private val context: Context) {

    private val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    private val SESION_ACTIVA = booleanPreferencesKey("sesion_activa")

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

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(NOMBRE_USUARIO)
            prefs[SESION_ACTIVA] = false
        }
    }
}
