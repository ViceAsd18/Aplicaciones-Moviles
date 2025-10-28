package com.example.alpha_mobile.data

import android.content.Context
import androidx.room.Room
import com.example.alpha_mobile.model.AppDatabase

// Se asegura de que solo exista una única instancia activa de la base de datos en toda la app.
object DB {

    //INSTANCE se marca como @Volatile para garantizar la visibilidad entre hilos.
    @Volatile
    private var INSTANCE: AppDatabase? = null

    //Devuelve la instancia de la base de datos (si ya existe), o la crea si no.
    fun get(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            //Doble verificación para crear la base de datos solo una vez.
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,        //Se usa el contexto de la app
                AppDatabase::class.java,    //Clase que define la bd (AppDataBase)
                "usuarios_db"               //nombre del archivo de la bd
            )
                //Si se cambia el esquema (entidades o estructura) y no hay migración,
                //se borra y recrea la base de datos (Para evitar errores entre las versiones).
                .fallbackToDestructiveMigration()
                //Construye la bd e inicia la instancia.
                .build()
                .also { INSTANCE = it }
        }
    }
}
