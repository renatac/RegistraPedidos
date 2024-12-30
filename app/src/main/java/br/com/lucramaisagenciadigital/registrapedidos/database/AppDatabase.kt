package br.com.lucramaisagenciadigital.registrapedidos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.SaleItemConverters

const val DB_NAME = "database-name"

@Database(
    entities = [UserData::class, SaleItem::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(SaleItemConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): RegisterOrdersDao
}