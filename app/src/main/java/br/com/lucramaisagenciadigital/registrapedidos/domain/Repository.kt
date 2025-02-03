package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.flow.Flow

interface Repository {
    val usersDataOrderByRequestNumber: Flow<List<UserData>>
    val saleItemsOrderByUserDataId: Flow<List<SaleItem>>
    suspend fun insertUserData(userData: UserData): Long
    suspend fun deleteUserDataByRequestNumber(requestNumber: Long)
    suspend fun deleteAllUserData()
    suspend fun insertSaleItem(saleItem: SaleItem): Long
    suspend fun deleteSaleItemByItemNumber(itemNumber: Long)
}