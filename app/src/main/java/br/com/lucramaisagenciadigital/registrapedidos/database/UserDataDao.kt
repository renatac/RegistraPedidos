package br.com.lucramaisagenciadigital.registrapedidos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {

    @Query("SELECT * FROM usersData ORDER BY requestNumber")
    fun getAllUsersDataOrderByRequestNumber(): Flow<List<UserData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(vararg userDataItem: UserData)

    @Query("SELECT * FROM usersData WHERE requestNumber=:number")
    suspend fun getUserDataByRequestNumber(number: Long): UserData

    @Query("SELECT * FROM usersData WHERE name=:userName")
    suspend fun getUserDataByName(userName: String): UserData

    @Query("DELETE FROM usersData WHERE requestNumber=:number")
    suspend fun deleteUserDataByRequestNumber(number: Long)

    @Query("DELETE FROM usersData WHERE name=:userName")
    suspend fun deleteUserDataByName(userName: String)

    @Query("DELETE FROM saleItem WHERE itemNumber=:number")
    suspend fun deleteSaleItemByItemNumber(number: Long)
}
