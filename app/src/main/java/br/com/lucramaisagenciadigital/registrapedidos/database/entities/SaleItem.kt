package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saleItem")
data class SaleItem(
    @PrimaryKey(autoGenerate = true) val itemNumber: Long? = 0L,
    val product: String,
    val quantity: Int,
    val unitaryValue: Double,
    val totalValue: Double
): Parcelable
