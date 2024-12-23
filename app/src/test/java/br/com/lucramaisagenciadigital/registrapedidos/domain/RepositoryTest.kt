package br.com.lucramaisagenciadigital.registrapedidos.database.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.domain.RepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryImplTest {

    private val registerOrdersDao: RegisterOrdersDao = mockk()
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = RepositoryImpl(registerOrdersDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `usersDataOrderByRequestNumber should return flow of list of UserData`() = runTest {
        val expectedUsersData = listOf(UserData(1, "User 1"), UserData(2, "User 2"))
        coEvery { registerOrdersDao.getAllUsersDataOrderByRequestNumber() } returns flowOf(
            expectedUsersData
        )

        val actualUsersData = repository.usersDataOrderByRequestNumber.first()

        assertEquals(expectedUsersData, actualUsersData)
        coVerify(exactly = 1) { registerOrdersDao.getAllUsersDataOrderByRequestNumber() }
    }

    @Test
    fun `saleItemsOrderByUserDataId should return flow of list of SaleItem`() = runTest {
        val expectedSaleItems =
            listOf(SaleItem(1, 1, "Item 1", 1, 1.0, 1.0), SaleItem(2, 1, "Item 2",1, 1.0, 1.0))
        coEvery { registerOrdersDao.getAllSaleItems() } returns flowOf(expectedSaleItems)

        val actualSaleItems = repository.saleItemsOrderByUserDataId.first()

        assertEquals(expectedSaleItems, actualSaleItems)
        coVerify(exactly = 1) { registerOrdersDao.getAllSaleItems() }
    }

    @Test
    fun `insertUserData should insert user data and return id`() = runTest {
        val userData = UserData(1, "User 1")
        val expectedId = 1L
        coEvery { registerOrdersDao.insertUserData(userData) } returns expectedId

        val actualId = repository.insertUserData(userData)

        assertEquals(expectedId, actualId)
        coVerify(exactly = 1) { registerOrdersDao.insertUserData(userData) }
    }

    @Test
    fun `getUserDataByRequestNumber should return user data by request number`() = runTest {
        val requestNumber = 1L
        val expectedUserData = UserData(1, "User 1")
        coEvery { registerOrdersDao.getUserDataByRequestNumber(requestNumber) } returns expectedUserData

        val actualUserData = repository.getUserDataByRequestNumber(requestNumber)

        assertEquals(expectedUserData, actualUserData)
        coVerify(exactly = 1) { registerOrdersDao.getUserDataByRequestNumber(requestNumber) }
    }

    @Test
    fun `getUserDataByRequestNumber should return null if user data not found`() = runTest {
        val requestNumber = 1L
        coEvery { registerOrdersDao.getUserDataByRequestNumber(requestNumber) } returns null

        val actualUserData = repository.getUserDataByRequestNumber(requestNumber)

        assertNull(actualUserData)
        coVerify(exactly = 1) { registerOrdersDao.getUserDataByRequestNumber(requestNumber) }
    }

    @Test
    fun `deleteUserDataByRequestNumber should delete user data by request number`() = runTest {
        val requestNumber = 1L
        coEvery { registerOrdersDao.deleteUserDataByRequestNumber(requestNumber) } returns Unit

        repository.deleteUserDataByRequestNumber(requestNumber)

        coVerify(exactly = 1) { registerOrdersDao.deleteUserDataByRequestNumber(requestNumber) }
    }

    @Test
    fun `deleteAllUserData should delete all user data`() = runTest {
        coEvery { registerOrdersDao.deleteAllUserData() } returns Unit

        repository.deleteAllUserData()

        coVerify(exactly = 1) { registerOrdersDao.deleteAllUserData() }
    }

    @Test
    fun `insertSaleItem should insert sale item and return id`() = runTest {
        val saleItem = SaleItem(1, 1, "Item 1", 1, 1.0, 1.0)
        val expectedId = 1L
        coEvery { registerOrdersDao.insertSaleItem(saleItem) } returns expectedId

        val actualId = repository.insertSaleItem(saleItem)

        assertEquals(expectedId, actualId)
        coVerify(exactly = 1) { registerOrdersDao.insertSaleItem(saleItem) }
    }

    @Test
    fun `deleteSaleItemByItemNumber should delete sale item by item number`() = runTest {
        val itemNumber = 1L
        coEvery { registerOrdersDao.deleteSaleItemByItemNumber(itemNumber) } returns Unit

        repository.deleteSaleItemByItemNumber(itemNumber)

        coVerify(exactly = 1) { registerOrdersDao.deleteSaleItemByItemNumber(itemNumber) }
    }
}