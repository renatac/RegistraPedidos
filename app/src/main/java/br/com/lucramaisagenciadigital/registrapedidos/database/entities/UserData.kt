package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.UserDataConverters

//@Parcelize
@Entity(tableName = "usersData")
@TypeConverters(UserDataConverters::class)
data class UserData(
    @PrimaryKey(autoGenerate = true) val requestNumber: Long = 0L,
    val name: String,
    @TypeConverters(UserDataConverters::class)
    val saleItemList: List<SaleItem>
)// Parcelable
