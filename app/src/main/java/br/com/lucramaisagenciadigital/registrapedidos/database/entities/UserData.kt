package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.UserDataConverters

@Entity(tableName = "usersData")
@TypeConverters(UserDataConverters::class)
data class UserData(
    @PrimaryKey(autoGenerate = true) val requestNumber: Long = 0L,
    val name: String,
    val saleItemList: List<SaleItem>
)
