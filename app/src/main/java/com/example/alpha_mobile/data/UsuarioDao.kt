package com.example.alpha_mobile.model

import androidx.room.*

// Define todas las operaciones que se pueden realizar sobre la tabla "usuarios" en la base de datos Room.
@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun obtenerUsuarioPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerTodosLosUsuarios(): List<Usuario>

    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)

    @Query("DELETE FROM usuarios")
    suspend fun eliminarTodos()
}
