package br.com.lucramaisagenciadigital.registrapedidos.database.entities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RegisterOrdersDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var registerOrdersDao: RegisterOrdersDao

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        // StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build()) // Removed this line
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        registerOrdersDao = database.getDao()
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.shutdown()
    }

    @Test
    fun `GIVEN_a_list_of_users_WHEN_getAllUsersDataOrderByRequestNumber_is_invoked_THEN_a_flow_of_users_ordered_by_request_number_should_be_returned`() = runTest {
        // Arrange
        val user1 = UserData(requestNumber = 2, name = "User B")
        val user2 = UserData(requestNumber = 1, name = "User A")
        registerOrdersDao.insertUserData(user1)
        registerOrdersDao.insertUserData(user2)

        // Act
        val actualUsers = registerOrdersDao.getAllUsersDataOrderByRequestNumber().first()

        // Assert
        assertEquals(listOf(user2, user1), actualUsers)
    }

    /*
    @Test
    fun `GIVEN_a_new_user_data_object_WHEN_insertUserData_is_invoked_THEN_the_user_should_be_inserted_and_the_generated_id_should_be_returned`() = runTest {
        // Arrange
        val newUser = UserData(name = "New User")

        // Act
        val generatedId = registerOrdersDao.insertUserData(newUser)

        // Assert
        val retrievedUser = registerOrdersDao.getUserDataByRequestNumber(generatedId)
        assertEquals(generatedId, retrievedUser?.requestNumber)
        assertEquals(newUser.name, retrievedUser?.name)
    }

    @Test
    fun `GIVEN_a_request_number_WHEN_getUserDataByRequestNumber_is_invoked_THEN_the_corresponding_user_data_should_be_returned`() = runTest {
        // Arrange
        val user = UserData(name = "Target User")
        val generatedId = registerOrdersDao.insertUserData(user)

        // Act
        val retrievedUser = registerOrdersDao.getUserDataByRequestNumber(generatedId)

        // Assert
        assertEquals(generatedId, retrievedUser?.requestNumber)
        assertEquals(user.name, retrievedUser?.name)
    }

    @Test
    fun `GIVEN_a_request_number_WHEN_deleteUserDataByRequestNumber_is_invoked_THEN_the_user_data_with_that_request_number_should_be_deleted`() = runTest {
        // Arrange
        val user = UserData(name = "User to Delete")
        val generatedId = registerOrdersDao.insertUserData(user)

        // Act
        registerOrdersDao.deleteUserDataByRequestNumber(generatedId)

        // Assert
        val retrievedUser = registerOrdersDao.getUserDataByRequestNumber(generatedId)
        assertEquals(null, retrievedUser)
    }

    @Test
    fun `WHEN_deleteAllUserData_is_invoked_THEN_all_user_data_should_be_deleted`() = runTest {
        // Arrange
        registerOrdersDao.insertUserData(UserData(name = "User 1"))
        registerOrdersDao.insertUserData(UserData(name = "User 2"))

        // Act
        registerOrdersDao.deleteAllUserData()

        // Assert
        val allUsers = registerOrdersDao.getAllUsersDataOrderByRequestNumber().first()
        assertEquals(emptyList<UserData>(), allUsers)
    }

    @Test
    fun `GIVEN_a_new_sale_item_object_WHEN_insertSaleItem_is_invoked_THEN_the_sale_item_should_be_inserted_and_the_generated_id_should_be_returned`() = runTest {
        // Arrange
        val user = UserData(name = "User")
        val generatedIdUser =registerOrdersDao.insertUserData(user)
        val newSaleItem = SaleItem(userDataId = generatedIdUser, product = "New Product", quantity = 1, unitaryValue = 10.0, totalValue = 10.0)

        // Act
        val generatedId = registerOrdersDao.insertSaleItem(newSaleItem)

        // Assert
        val allSaleItems = registerOrdersDao.getAllSaleItems().first()
        val retrievedSaleItem = allSaleItems.find { it.itemNumber == generatedId }
        assertEquals(generatedId, retrievedSaleItem?.itemNumber)
        assertEquals(newSaleItem.product, retrievedSaleItem?.product)
    }

    @Test
    fun `GIVEN_a_list_of_sale_items_WHEN_getAllSaleItems_is_invoked_THEN_a_flow_of_sale_items_ordered_by_user_data_id_should_be_returned`() = runTest {
        // Arrange
        val user1 = UserData(name = "User 1")
        val user2 = UserData(name = "User 2")
        val generatedIdUser1 = registerOrdersDao.insertUserData(user1)
        val generatedIdUser2 = registerOrdersDao.insertUserData(user2)
        val saleItem1 = SaleItem(userDataId = generatedIdUser1, product = "Product Alpha", quantity = 1, unitaryValue = 10.0, totalValue = 10.0)
        val saleItem2 = SaleItem(userDataId = generatedIdUser2, product = "Product Beta", quantity = 2, unitaryValue = 5.0, totalValue = 10.0)
        registerOrdersDao.insertSaleItem(saleItem1)
        registerOrdersDao.insertSaleItem(saleItem2)

        // Act
        val actualSaleItems = registerOrdersDao.getAllSaleItems().first()

        // Assert
        assertEquals(listOf(saleItem1, saleItem2), actualSaleItems)
    }

    @Test
    fun `GIVEN_a_request_number_WHEN_getUserDataWithSaleItems_is_invoked_THEN_the_corresponding_user_data_with_sale_items_should_be_returned`() =
        runTest {
            // Arrange
            val user = UserData(name = "Associated User")
            val generatedIdUser = registerOrdersDao.insertUserData(user)
            val saleItem = SaleItem(
                userDataId = generatedIdUser,
                product = "Associated Product",
                quantity = 1,
                unitaryValue = 10.0,
                totalValue = 10.0
            )
            val generatedIdSaleItem = registerOrdersDao.insertSaleItem(saleItem)

            // Act
            val actualUserDataWithSaleItems =
                registerOrdersDao.getUserDataWithSaleItems(generatedIdUser)

            // Assert
            assertEquals(user.requestNumber, actualUserDataWithSaleItems?.userData?.requestNumber)
            assertEquals(listOf(saleItem), actualUserDataWithSaleItems?.saleItems)
        }
     */
}
