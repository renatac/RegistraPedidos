package br.com.lucramaisagenciadigital.registrapedidos.di

import android.app.Application
import androidx.room.Room
import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.RegisterOrdersDao
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.domain.RepositoryImpl
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

@RunWith(JUnit4::class)
class ModuleTest : KoinTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var application: Application

    @Before
    fun setup() {
        application = mockk(relaxed = true)
        startKoin {
            androidContext(application)
            modules(getUserDataModules())
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Check AppDatabase's creation`() {
        appDatabase = get()
        assertNotNull(appDatabase)
    }

    @Test
    fun `Check RegisterOrdersDao's creation`() {
        val registerOrdersDao = get<RegisterOrdersDao>()
        assertNotNull(registerOrdersDao)
    }

    @Test
    fun `Check Repository's creation`() {
        val repository = get<Repository>()
        assertNotNull(repository)
    }

    @Test
    fun `Check UserDataViewModel's creation`() {
        val userDataViewModel = get<UserDataViewModel>()
        assertNotNull(userDataViewModel)
    }
}

private val databaseModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidApplication(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    single { get<AppDatabase>().getDao() }
}

private val dataModule = module {
    single<Repository> { RepositoryImpl(get()) }
}

private val presentationModule = module {
    single<UserDataViewModel> { UserDataViewModel(repository = get()) }
}

fun getUserDataModules() = listOf(databaseModule, dataModule, presentationModule)