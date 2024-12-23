package br.com.lucramaisagenciadigital.registrapedidos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserDataWithSaleItems
import kotlinx.coroutines.flow.Flow

@Dao
interface RegisterOrdersDao {

    // UserData Queries
    @Query("SELECT * FROM usersData ORDER BY requestNumber")
    fun getAllUsersDataOrderByRequestNumber(): Flow<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userDataItem: UserData): Long

    @Query("SELECT * FROM usersData WHERE requestNumber=:number")
    suspend fun getUserDataByRequestNumber(number: Long): UserData?

    @Query("DELETE FROM usersData WHERE requestNumber=:number")
    suspend fun deleteUserDataByRequestNumber(number: Long)

    @Query("DELETE FROM usersData")
    suspend fun deleteAllUserData()

    // SaleItem Query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaleItem(saleItem: SaleItem): Long

    @Query("SELECT * FROM saleItem ORDER BY userDataId")
    fun getAllSaleItems(): Flow<List<SaleItem>>

    @Transaction
    @Query("SELECT * FROM usersData WHERE requestNumber = :requestNumber")
    suspend fun getUserDataWithSaleItems(requestNumber: Long): UserDataWithSaleItems?

    @Query("DELETE FROM saleItem WHERE itemNumber = :itemNumber")
    suspend fun deleteSaleItemByItemNumber(itemNumber: Long)
}
