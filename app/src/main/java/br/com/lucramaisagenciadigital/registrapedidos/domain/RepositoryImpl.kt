package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.flow.Flow


class RepositoryImpl(private val database: AppDatabase) : Repository {

    override val usersDataOrderByRequestNumber: Flow<List<UserData>> =
        database.getDao().getAllUsersDataOrderByRequestNumber()

    override suspend fun insertUserData(userData: UserData) {
        database.getDao().insertUserData(userData)
    }

    override suspend fun getUserDataByRequestNumber(requestNumber: Long): UserData {
        return database.getDao().getUserDataByRequestNumber(requestNumber)
    }

    override suspend fun getUserDataByUserName(name: String): UserData {
        return database.getDao().getUserDataByName(name)
    }

    override suspend fun deleteUserDataByRequestNumber(requestNumber: Long) {
        return database.getDao().deleteUserDataByRequestNumber(requestNumber)
    }

    override suspend fun deleteUserDataByName(name: String) {
        return database.getDao().deleteUserDataByName(name)
    }

    override suspend fun deleteSaleItemByItemNumber(itemNumber: Long) {
        return database.getDao().deleteSaleItemByItemNumber(itemNumber)
    }
}