package br.com.lucramaisagenciadigital.registrapedidos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData

const val DB_NAME = "database-name"
private lateinit var INSTANCE: AppDatabase

@Database(
    entities = [UserData::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): UserDataDao

    fun getDatabase(context: Context): AppDatabase {
        synchronized(AppDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }
        }
        return INSTANCE
    }
}