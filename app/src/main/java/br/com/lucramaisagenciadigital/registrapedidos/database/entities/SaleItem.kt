package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.TypeConverters
import br.com.lucramaisagenciadigital.registrapedidos.database.utils.UserDataConverters

@TypeConverters(UserDataConverters::class)
data class SaleItem(
    val itemNumber: Long? = 0L,
    val product: String,
    val quantity: Int,
    val unitaryValue: Double,
    val totalValue: Double
)