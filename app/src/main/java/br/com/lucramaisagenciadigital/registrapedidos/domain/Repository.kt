package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserDataWithSaleItems
import kotlinx.coroutines.flow.Flow

interface Repository {
    val usersDataOrderByRequestNumber: Flow<List<UserData>>
    val saleItemsOrderByUserDataId: Flow<List<SaleItem>>
    suspend fun insertUserData(userData: UserData): Long
    suspend fun getUserDataByRequestNumber(requestNumber: Long): UserData?
    suspend fun getUserDataByUserName(name: String): UserData?
    suspend fun deleteUserDataByRequestNumber(requestNumber: Long)
    suspend fun deleteUserDataByName(name: String)
    suspend fun insertSaleItem(saleItem: SaleItem): Long
    suspend fun getUserDataWithSaleItems(requestNumber: Long): UserDataWithSaleItems?
    suspend fun deleteSaleItem(saleItem: SaleItem)
}