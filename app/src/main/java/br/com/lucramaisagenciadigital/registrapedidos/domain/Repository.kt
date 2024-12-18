package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.flow.Flow

interface Repository {
    val usersDataOrderByRequestNumber: Flow<List<UserData>>
    suspend fun insertUserData(userData: UserData)
    suspend fun getUserDataByRequestNumber(requestNumber: Long): UserData
    suspend fun getUserDataByUserName(name: String): UserData
    suspend fun deleteUserDataByRequestNumber(requestNumber: Long)
    suspend fun deleteUserDataByName(name: String)
  //  suspend fun deleteSaleItemByItemNumber(itemNumber: Long)
}