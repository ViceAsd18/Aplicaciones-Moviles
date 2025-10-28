package com.example.alpha_mobile.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class], //Lista de entidades (tablas) que usará la base de datos
    version = 1,                 //Versión de la base de datos
    exportSchema = false         //Se desactiva la exportación del esquema para evitar archivos innecesarios
)
abstract class AppDatabase : RoomDatabase() {
    //Función abstracta que retorna el DAO (Data Access Object) asociado a la entidad Usuario.
    //Para ejecutar operaciones como insertar, buscar o eliminar usuarios.
    abstract fun usuarioDao(): UsuarioDao
}
