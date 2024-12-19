package br.com.lucramaisagenciadigital.registrapedidos.di

import androidx.room.Room
import br.com.lucramaisagenciadigital.registrapedidos.database.AppDatabase
import br.com.lucramaisagenciadigital.registrapedidos.database.DB_NAME
import br.com.lucramaisagenciadigital.registrapedidos.domain.Repository
import br.com.lucramaisagenciadigital.registrapedidos.domain.RepositoryImpl
import br.com.lucramaisagenciadigital.registrapedidos.presentation.viewmodel.UserDataViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
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
