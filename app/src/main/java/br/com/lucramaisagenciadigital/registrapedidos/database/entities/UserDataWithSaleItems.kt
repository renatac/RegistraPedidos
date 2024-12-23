package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserDataWithSaleItems(
    @Embedded val userData: UserData,
    @Relation(
        parentColumn = "requestNumber",
        entityColumn = "userDataId"
    )
    val saleItems: List<SaleItem>
)