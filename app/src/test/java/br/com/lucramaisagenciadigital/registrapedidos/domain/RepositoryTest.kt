package br.com.lucramaisagenciadigital.registrapedidos.domain

import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.expectedId
import br.com.lucramaisagenciadigital.registrapedidos.expectedRequestNumber
import br.com.lucramaisagenciadigital.registrapedidos.itemNumber
import br.com.lucramaisagenciadigital.registrapedidos.newSaleItem
import br.com.lucramaisagenciadigital.registrapedidos.newUserData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryImplAdvancedTest {

    private lateinit var repository: RepositoryImpl
    private lateinit var mockRegisterOrdersDao: RegisterOrdersDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRegisterOrdersDao = mockk(relaxed = true)
        repository = RepositoryImpl(mockRegisterOrdersDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Repository's insertUserData should call dao's insertUserData and return the generated id, when a new UserData is passed`() =
        runTest {
            coEvery { mockRegisterOrdersDao.insertUserData(newUserData) } returns expectedId

            // ACT
            val actualGeneratedId = repository.insertUserData(newUserData)

            // ASSERT
            assertEquals(expectedId, actualGeneratedId)
            coVerify(exactly = 1) { mockRegisterOrdersDao.insertUserData(newUserData) }
        }

    @Test
    fun `Repository's insertUserData should call dao's deleteUserDataByRequestNumber, when a request number is passed`() =
        runTest {
            // ACT
            repository.deleteUserDataByRequestNumber(expectedRequestNumber)

            // ASSERT
            coVerify(exactly = 1) {
                mockRegisterOrdersDao.deleteUserDataByRequestNumber(
                    expectedRequestNumber
                )
            }
        }

    @Test
    fun `Repository's deleteAllUserData should call dao's deleteAllUserData`() =
        runTest {
            // ACT
            repository.deleteAllUserData()

            // ASSERT
            coVerify(exactly = 1) { mockRegisterOrdersDao.deleteAllUserData() }
        }

    @Test
    fun `Repository's insertSaleItem should call dao's insertSaleItem and return the generated id, when a new SaleItem is passed`() =
        runTest {
            // ARRANGE
            coEvery { mockRegisterOrdersDao.insertSaleItem(newSaleItem) } returns expectedId

            // ACT
            val actualGeneratedId = repository.insertSaleItem(newSaleItem)

            // ASSERT
            assertEquals(expectedId, actualGeneratedId)
            coVerify(exactly = 1) { mockRegisterOrdersDao.insertSaleItem(newSaleItem) }
        }

    @Test
    fun `Repository's deleteSaleItemByItemNumber should call dao's deleteSaleItemByItemNumber, when an item number is passed`() =
        runTest {
            // ACT
            repository.deleteSaleItemByItemNumber(itemNumber)

            // ASSERT
            coVerify(exactly = 1) {
                mockRegisterOrdersDao.deleteSaleItemByItemNumber(
                    itemNumber
                )
            }
        }
}