import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.SaleItem
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserData
import br.com.lucramaisagenciadigital.registrapedidos.database.entities.UserDataWithSaleItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RegisterOrdersDaoRelationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var registerOrdersDao: RegisterOrdersDao

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
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
    fun `getUserDataWithSaleItems should return user data with sale items`() = runTest {
        // Given
        val userData = UserData(name = "User 1")
        val userId = registerOrdersDao.insertUserData(userData)

        val saleItem1 = SaleItem(0L, userDataId = userId, "p1", 1, 1.0, 1.0)
        val saleItem2 = SaleItem(1L, userDataId = userId, "p2", 1, 1.0, 1.0)
        registerOrdersDao.insertSaleItem(saleItem1)
        registerOrdersDao.insertSaleItem(saleItem2)

        // When
        val userDataWithSaleItems = registerOrdersDao.getUserDataWithSaleItems(userId)

        // Then
        val expectedUserData = userData.copy(requestNumber = userId)
        val expectedSaleItems =
            listOf(saleItem1.copy(itemNumber = 1), saleItem2.copy(itemNumber = 2))
        val expectedResult = UserDataWithSaleItems(expectedUserData, expectedSaleItems)
        assertEquals(expectedResult, userDataWithSaleItems)
    }

    @Test
    fun `getUserDataWithSaleItems should return empty list of sale items if none exist`() =
        runTest {
            // Given
            val userData = UserData(name = "User 1")
            val userId = registerOrdersDao.insertUserData(userData)

            // When
            val userDataWithSaleItems = registerOrdersDao.getUserDataWithSaleItems(userId)

            // Then
            val expectedUserData = userData.copy(requestNumber = userId)
            val expectedResult = UserDataWithSaleItems(expectedUserData, emptyList())
            assertEquals(expectedResult, userDataWithSaleItems)
        }
}