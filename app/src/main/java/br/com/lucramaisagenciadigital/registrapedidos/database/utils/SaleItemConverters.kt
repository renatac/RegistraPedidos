package br.com.lucramaisagenciadigital.registrapedidos.database.utils

import androidx.room.TypeConverter
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
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
    /*@TypeConverter
    fun fromSaleItemList(saleItemList: List<SaleItem>?): String? {
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

   @TypeConverter
    fun fromUserData(userData: UserData): String {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<UserData>() {}.type
        return gson.toJson(userData, type)
    }

    @TypeConverter
    fun toUserData(userDataString: String): UserData {
        val gson = Gson()
        val type = object : com.google.gson.reflect.TypeToken<UserData>() {}.type
        return gson.fromJson(userDataString, type)
    }*/
}
