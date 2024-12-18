package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.UserDataConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "usersData")
@TypeConverters(UserDataConverters::class)
data class UserData(
    @PrimaryKey(autoGenerate = true) val requestNumber: Long = 0L,
    val name: String,
    val saleItemList: List<SaleItem>
): Parcelable
