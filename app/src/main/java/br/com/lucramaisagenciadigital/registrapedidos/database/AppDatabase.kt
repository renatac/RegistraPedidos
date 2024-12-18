package br.com.lucramaisagenciadigital.registrapedidos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData

private const val DB_NAME = "database-name"

@Database(
    entities = [UserData::class],
    [SaleItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun getDao(): UserDataDao
}

private lateinit var INSTANCE: AppDatabase

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
