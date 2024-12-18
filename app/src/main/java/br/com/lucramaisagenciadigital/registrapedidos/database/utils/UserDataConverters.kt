package br.com.lucramaisagenciadigital.registrapedidos.database.utils

import androidx.room.TypeConverter
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import com.google.gson.Gson

class UserDataConverters {

    @TypeConverter
    fun fromSaleItemList(saleItemList: List<SaleItem>): String {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<List<SaleItem>>() {}.type
        return gson.toJson(saleItemList, type)
    }

    @TypeConverter
    fun toSaleItemList(saleItemListString: String): List<SaleItem> {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<List<SaleItem>>() {}.type
        return gson.fromJson(saleItemListString, type)
    }
}
