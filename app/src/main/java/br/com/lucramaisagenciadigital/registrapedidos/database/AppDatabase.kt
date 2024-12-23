package br.com.lucramaisagenciadigital.registrapedidos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.SaleItemConverters

const val DB_NAME = "database-name"
private lateinit var INSTANCE: AppDatabase

@Database(
    entities = [UserData::class, SaleItem::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(SaleItemConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): RegisterOrdersDao

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