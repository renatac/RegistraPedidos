package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.UserDataDao
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val userDataDao: UserDataDao) : Repository {

    override val usersDataOrderByRequestNumber: Flow<List<UserData>> =
        userDataDao.getAllUsersDataOrderByRequestNumber()

    override suspend fun insertUserData(userData: UserData) {
        userDataDao.insertUserData(userData)
    }

    override suspend fun getUserDataByRequestNumber(requestNumber: Long): UserData {
        return userDataDao.getUserDataByRequestNumber(requestNumber)
    }

    override suspend fun getUserDataByUserName(name: String): UserData {
        return userDataDao.getUserDataByName(name)
    }

    override suspend fun deleteUserDataByRequestNumber(requestNumber: Long) {
        return userDataDao.deleteUserDataByRequestNumber(requestNumber)
    }

    override suspend fun deleteUserDataByName(name: String) {
        return userDataDao.deleteUserDataByName(name)
    }
}