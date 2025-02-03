package br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel

import br.com.lucramaisagenciadigital.registrapedidos.clientName
import br.com.lucramaisagenciadigital.registrapedidos.clientNameNull
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.expectedId
import br.com.lucramaisagenciadigital.registrapedidos.expectedRequestNumber
import br.com.lucramaisagenciadigital.registrapedidos.itemNumber
import br.com.lucramaisagenciadigital.registrapedidos.newSaleItem
import br.com.lucramaisagenciadigital.registrapedidos.newUserData
import br.com.lucramaisagenciadigital.registrapedidos.presentation.ZERO_DOUBLE
import br.com.lucramaisagenciadigital.registrapedidos.productName
import br.com.lucramaisagenciadigital.registrapedidos.quantityText
import br.com.lucramaisagenciadigital.registrapedidos.saleItems
import br.com.lucramaisagenciadigital.registrapedidos.totalQuantity
import br.com.lucramaisagenciadigital.registrapedidos.unitValueText
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
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDataViewModelAdvancedTest {

    private lateinit var viewModel: UserDataViewModel
    private lateinit var mockRepository: Repository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
        viewModel = UserDataViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `AdjustDiscount should adjust the discount correctly when isAddingDiscount is false, then each SaleItem totalValue should be added by the discount percentage`() =
        runTest {
            val discount = 30.0
            val totalValueExpected1 = 20.0
            val totalValueExpected2 = 40.0

            // ACT
            viewModel.adjustDiscount(discount, saleItems, false)

            // ASSERT
            assertEquals(totalValueExpected1, saleItems[0].totalValue, ZERO_DOUBLE)
            assertEquals(totalValueExpected2, saleItems[1].totalValue, ZERO_DOUBLE)
        }

    @Test
    fun `insertUserData should insert a new UserData and return its ID`() =
        runTest {
            // ARRANGE
            coEvery { mockRepository.insertUserData(newUserData) } returns expectedId

            // ACT
            val actualId = viewModel.insertUserData(newUserData)

            // ASSERT
            assertEquals(expectedId, actualId)
            assertEquals(expectedId, viewModel.userDataId)
            coVerify(exactly = 1) { mockRepository.insertUserData(newUserData) }
        }

    @Test
    fun `viewmodel's deleteUserDataByRequestNumber is called, so repository's deleteUserDataByRequestNumber is called once`() =
        runTest {
            // ACT
            viewModel.deleteUserDataByRequestNumber(expectedRequestNumber)

            // ASSERT
            coVerify(exactly = 1) {
                mockRepository.deleteUserDataByRequestNumber(
                    expectedRequestNumber
                )
            }
        }

    @Test
    fun `viewmodel's deleteAllUserData is called, so repository's deleteAllUserData is called once`() =
        runTest {
            // ACT
            viewModel.deleteAllUserData()

            // ASSERT
            coVerify(exactly = 1) { mockRepository.deleteAllUserData() }
        }

    @Test
    fun `insertSaleItem should insert a new SaleItem and return it ID`() =
        runTest {
            coEvery { mockRepository.insertSaleItem(newSaleItem) } returns expectedId

            // ACT
            val actualId = viewModel.insertSaleItem(newSaleItem)

            // ASSERT
            assertEquals(expectedId, actualId)
            assertEquals(expectedId, viewModel.saleDataId)
            coVerify(exactly = 1) { mockRepository.insertSaleItem(newSaleItem) }
        }

    @Test
    fun `viewmodel's deleteSaleItemByItemNumber is called, so repository's deleteSaleItemByItemNumber is called once`() =
        runTest {
            // ACT
            viewModel.deleteSaleItemByItemNumber(itemNumber)

            // ASSERT
            coVerify(exactly = 1) {
                mockRepository.deleteSaleItemByItemNumber(
                    itemNumber
                )
            }
        }

    @Test
    fun `ClearFields should reset all fields to their initial values, including clientName, when it is not null`() =
        runTest {
            // ACT
            viewModel.clearFields(
                clientName,
                productName,
                quantityText,
                unitValueText,
                totalQuantity
            )

            // ASSERT
            assertEquals(String(), clientName.value)
            assertEquals(String(), productName.value)
            assertEquals(String(), quantityText.value)
            assertEquals(String(), unitValueText.value)
            assertEquals(ZERO_DOUBLE, totalQuantity.value, ZERO_DOUBLE)
        }

    @Test
    fun `ClearFields should reset all fields to their initial values, excepts clientName, when it is null`() =
        runTest {
            // ACT
            viewModel.clearFields(
                clientName,
                productName,
                quantityText,
                unitValueText,
                totalQuantity
            )

            // ASSERT
            assertNull(clientNameNull)
            assertEquals(String(), productName.value)
            assertEquals(String(), quantityText.value)
            assertEquals(String(), unitValueText.value)
            assertEquals(ZERO_DOUBLE, totalQuantity.value, ZERO_DOUBLE)
        }
}
