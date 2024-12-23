package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "saleItem",
    foreignKeys = [ForeignKey(
        entity = UserData::class,
        parentColumns = ["requestNumber"],
        childColumns = ["userDataId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userDataId")]
)
data class SaleItem(
    @PrimaryKey(autoGenerate = true) val itemNumber: Long = 0L,
    val userDataId: Long,
    val product: String,
    val quantity: Int,
    val unitaryValue: Double,
    val totalValue: Double
)