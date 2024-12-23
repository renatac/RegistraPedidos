package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RepositoryImpl(private val registerOrdersDao: RegisterOrdersDao) : Repository {

    override val usersDataOrderByRequestNumber: Flow<List<UserData>> =
        registerOrdersDao.getAllUsersDataOrderByRequestNumber()

    override val saleItemsOrderByUserDataId: Flow<List<SaleItem>> =
        registerOrdersDao.getAllSaleItems()

    override suspend fun insertUserData(userData: UserData): Long {
        return withContext(Dispatchers.IO) {
            registerOrdersDao.insertUserData(userData)
        }
    }

    override suspend fun getUserDataByRequestNumber(requestNumber: Long): UserData? {
        return registerOrdersDao.getUserDataByRequestNumber(requestNumber)
    }

    override suspend fun deleteUserDataByRequestNumber(requestNumber: Long) {
        return registerOrdersDao.deleteUserDataByRequestNumber(requestNumber)
    }

    override suspend fun deleteAllUserData() {
        return registerOrdersDao.deleteAllUserData()
    }

    override suspend fun insertSaleItem(saleItem: SaleItem): Long {
        return withContext(Dispatchers.IO) {
            registerOrdersDao.insertSaleItem(saleItem)
        }
    }

    override suspend fun deleteSaleItemByItemNumber(itemNumber: Long) {
        return registerOrdersDao.deleteSaleItemByItemNumber(itemNumber)
    }
}