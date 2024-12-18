package br.com.lucramaisagenciadigital.registrapedidos.database.utils

import androidx.room.TypeConverter
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem

class UserDataConverters {

    @TypeConverter
    fun fromSaleItemItem(instance: SaleItem): String {
        return instance.toJson()
    }

    @TypeConverter
    fun toSaleItemItem(instance: String): SaleItem {
        return instance.fromJson()
    }
}
