package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.os.StrictMode

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RegisterOrdersDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var registerOrdersDao: RegisterOrdersDao

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        registerOrdersDao = database.getDao()
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun `insertUserData should insert user data and retrieve it`() = runBlocking {
        val userData = UserData(name = "User 1")
        val id = registerOrdersDao.insertUserData(userData)

        val retrievedUserData = registerOrdersDao.getUserDataByRequestNumber(id)

        assertEquals(userData.copy(requestNumber = id), retrievedUserData)
    }

    @Test
    fun `getUserDataByRequestNumber should return null if user data not found`() = runBlocking {
        val retrievedUserData = registerOrdersDao.getUserDataByRequestNumber(1L)

        assertNull(retrievedUserData)
    }

    @Test
    fun `getAllUsersDataOrderByRequestNumber should return all users data`() = runBlocking {
        val userData1 = UserData(name = "User 1")
        val userData2 = UserData(name = "User 2")
        registerOrdersDao.insertUserData(userData1)
        registerOrdersDao.insertUserData(userData2)

        val allUsersData = registerOrdersDao.getAllUsersDataOrderByRequestNumber().first()

        assertEquals(2, allUsersData.size)
    }

    @Test
    fun `deleteUserDataByRequestNumber should delete user data`() = runBlocking {
        val userData = UserData(name = "User 1")
        val id = registerOrdersDao.insertUserData(userData)

        registerOrdersDao.deleteUserDataByRequestNumber(id)
        val retrievedUserData = registerOrdersDao.getUserDataByRequestNumber(id)

        assertNull(retrievedUserData)
    }

    @Test
    fun `deleteAllUserData should delete all user data`() = runBlocking {
        val userData1 = UserData(name = "User 1")
        val userData2 = UserData(name = "User 2")
        registerOrdersDao.insertUserData(userData1)
        registerOrdersDao.insertUserData(userData2)

        registerOrdersDao.deleteAllUserData()
        val allUsersData = registerOrdersDao.getAllUsersDataOrderByRequestNumber().first()

        assertEquals(0, allUsersData.size)
    }
}