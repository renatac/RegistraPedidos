package br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDataViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: Repository = mockk()
    private lateinit var viewModel: UserDataViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UserDataViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insertUserData should insert user data and update userDataId`() = runTest {
        val userData = UserData(name = "User 1")
        val expectedId = 1L
        coEvery { repository.insertUserData(userData) } returns expectedId

        val actualId = viewModel.insertUserData(userData)

        assertEquals(expectedId, actualId)
        assertEquals(expectedId, viewModel.userDataId)
        coVerify(exactly = 1) { repository.insertUserData(userData) }
    }

    @Test
    fun `deleteUserDataByRequestNumber should call repository delete function`() = runTest {
        val requestNumber = 1L
        coEvery { repository.deleteUserDataByRequestNumber(requestNumber) } returns Unit

        viewModel.deleteUserDataByRequestNumber(requestNumber)

        coVerify(exactly = 1) { repository.deleteUserDataByRequestNumber(requestNumber) }
    }

    @Test
    fun `deleteAllUserData should call repository delete all function`() = runTest {
        coEvery { repository.deleteAllUserData() } returns Unit

        viewModel.deleteAllUserData()

        coVerify(exactly = 1) { repository.deleteAllUserData() }
    }

    @Test
    fun `insertSaleItem should insert sale item and update saleDataId`() = runTest {
        val saleItem = SaleItem(1L, userDataId = 1, "Item 1", 1, 1.0, 1.0)
        val expectedId = 1L
        coEvery { repository.insertSaleItem(saleItem) } returns expectedId

        val actualId = viewModel.insertSaleItem(saleItem)

        assertEquals(expectedId, actualId)
        assertEquals(expectedId, viewModel.saleDataId)
        coVerify(exactly = 1) { repository.insertSaleItem(saleItem) }
    }

    @Test
    fun `deleteSaleItemByItemNumber should call repository delete function`() = runTest {
        val itemNumber = 1L
        coEvery { repository.deleteSaleItemByItemNumber(itemNumber) } returns Unit

        viewModel.deleteSaleItemByItemNumber(itemNumber)

        coVerify(exactly = 1) { repository.deleteSaleItemByItemNumber(itemNumber) }
    }

    @Test
    fun `allUsersDataOrderByRequestNumber should return flow of list of UserData`() = runTest {
        val expectedUsersData = listOf(UserData(1, "User 1"), UserData(2, "User 2"))
        coEvery { repository.usersDataOrderByRequestNumber } returns flowOf(expectedUsersData)

        val actualUsersData = viewModel.allUsersDataOrderByRequestNumber

        assertEquals(expectedUsersData, actualUsersData)
        coVerify(exactly = 1) { repository.usersDataOrderByRequestNumber }
    }

    @Test
    fun `allSaleItemsOrderByUserDataId should return flow of list of SaleItem`() = runTest {
        val expectedSaleItems =
            listOf(SaleItem(1, 1, "Item 1", 1, 1.0, 1.0), SaleItem(2, 1, "Item 2", 1, 1.0, 1.0))
        coEvery { repository.saleItemsOrderByUserDataId } returns flowOf(expectedSaleItems)

        val actualSaleItems = viewModel.allSaleItemsOrderByUserDataId

        assertEquals(expectedSaleItems, actualSaleItems)
        coVerify(exactly = 1) { repository.saleItemsOrderByUserDataId }
    }
}