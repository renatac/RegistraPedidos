package br.com.lucramaisagenciadigital.registrapedidos.database.utils

import androidx.room.TypeConverter
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SaleItemConverters {

    @TypeConverter
    fun fromString(value: String?): List<SaleItem>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<SaleItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<SaleItem>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }
}
